package co.test.spring

import org.junit.jupiter.api.Test
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

class ReentrantLockTest {

    private fun getRunnable(lock: ReentrantLock, mainThreadLock: ReentrantLock, mainCondition: Condition): Runnable {
        return Runnable {
            try {
                println("[" + Thread.currentThread().name + "]: lock 획득 시도")
                lock.lockInterruptibly()
                try {
                    println("[" + Thread.currentThread().name + "]: lock 획득")
                    Thread.sleep(3000)

                    for (i in 0..4) {
                        println("[holdCount: ${ mainThreadLock.holdCount } isLocked: ${ mainThreadLock.isLocked } isHeldByCurrentThread: ${ mainThreadLock.isHeldByCurrentThread }] ")
                        Thread.sleep(100)
                    }

                    mainThreadLock.lock()
                    try {
                        println("[" + Thread.currentThread().name + "]: main 스레드 깨움")
                        mainCondition.signalAll()
                    } finally {
                        mainThreadLock.unlock()
                    }
                } finally {
                    println("[" + Thread.currentThread().name + "]: lock 해제")
                    lock.unlock()
                }
            } catch (ignored: InterruptedException) {
                println("[" + Thread.currentThread().name + "]: interrupt 발생")
            }
        }
    }

    @Test
    fun `기본적인 lock 테스트`() {
        val mainThreadLock = ReentrantLock()
        val mainCondition = mainThreadLock.newCondition()

        val childThreadLock = ReentrantLock()

        val t1 = Thread(getRunnable(childThreadLock, mainThreadLock, mainCondition), "t1")
        val t2 = Thread(getRunnable(childThreadLock, mainThreadLock, mainCondition), "t2")

        mainThreadLock.lock()
        try {
            t1.start()
            Thread.sleep(100)
            t2.start()
            Thread.sleep(2000)
            t2.interrupt()

            println("[main]: 대기 시작")
            mainCondition.await()
            println("[main]: 깨어남")
        } catch (ignored: Exception) {
        } finally {
            mainThreadLock.unlock()
        }
        println("[main]: 종료")
    }
}