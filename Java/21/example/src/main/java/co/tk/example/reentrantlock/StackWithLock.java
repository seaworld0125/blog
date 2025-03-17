package co.tk.example.reentrantlock;

import java.util.Stack;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class StackWithLock {

  private Stack<String> stack;
  private ReentrantLock lock;
  private Condition consumerCondition;
  private Condition producerCondition;
  private final int capacity;

  public StackWithLock(int capacity) {
    this.stack = new Stack<>();
    this.lock = new ReentrantLock();
    this.consumerCondition = lock.newCondition();
    this.producerCondition = lock.newCondition();
    this.capacity = capacity;
  }

  public void push(String item) {
    lock.lock();
    try {
      while(stack.size() == capacity) {
        producerCondition.await();
      }
      stack.push(item);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }

  public String pop() {
    lock.lock();
    try {
      while(stack.isEmpty()) {
        consumerCondition.await();
      }
      return stack.pop();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    ReentrantLock childThreadLock = new ReentrantLock();
    ReentrantLock mainThreadLock = new ReentrantLock();
    Condition mainCondition = mainThreadLock.newCondition();

    Thread t1 = new Thread(getRunnable(childThreadLock, mainThreadLock, mainCondition), "t1");
    Thread t2 = new Thread(getRunnable(childThreadLock, mainThreadLock, mainCondition), "t2");

    mainThreadLock.lock(); // ✅ main 스레드가 먼저 락을 잡고 대기
    try {
      t1.start();
      Thread.sleep(100);
      t2.start();
      Thread.sleep(2000);
      t2.interrupt();

      System.out.println("[main]: 대기 시작");
      mainCondition.await();
      System.out.println("[main]: 깨어남");

    } catch (Exception ignored) {
    } finally {
      mainThreadLock.unlock();
    }
    System.out.println("[main]: 종료");
  }

  private static Runnable getRunnable(ReentrantLock lock, ReentrantLock mainThreadLock, Condition mainCondition) {
    return () -> {
      try {
        System.out.println("[" + Thread.currentThread().getName() + "]: lock 획득 시도");
        lock.lockInterruptibly();
        try {
          System.out.println("[" + Thread.currentThread().getName() + "]: lock 획득");
          Thread.sleep(3000); // ✅ 작업 수행

          // ✅ main 스레드를 깨우기 위해 signalAll() 호출
          mainThreadLock.lock();
          try {
            System.out.println("[" + Thread.currentThread().getName() + "]: main 스레드 깨움");
            mainCondition.signalAll();
          } finally {
            mainThreadLock.unlock();
          }

        } finally {
          System.out.println("[" + Thread.currentThread().getName() + "]: lock 해제");
          lock.unlock();
        }
      } catch (InterruptedException ignored) {
        System.out.println("[" + Thread.currentThread().getName() + "]: interrupt 발생");
      }
    };
  }
}
