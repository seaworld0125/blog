### 꼬리 재귀 적용하기
- 재귀 프로세스를 실행하는 데 필요한 메모리를 최소화하자.
- 꼬리 재귀를 사용해 알고리즘을 표현하고 해당 함수에 tailrec 키워드를 추가한다.
- 꼬리 재귀는 콜 스택에 새 스택 프레임을 추가하지 않게 구현하는 특별한 종류의 재귀이다.

```kotlin
tailrec fun factorial(n: Long, acc: BigInteger = BigInteger.ONE): BigInteger =
	when (n) {
		0L -> BigInteger.ONE
		1L -> acc
		else -> factorial(n - 1, acc * BigInteger.valueOf(n))
	}
```

위 코드에서 핵심은 tailrec 키워드이다. 컴파일러가 재귀를 최적화해야 하는지 알 수 있게 해준다. 바이트코드를 생성하고 자바로 디컴파일하면 결과는 while 루프를 사용하는 반복 알고리즘으로 최적화 된다. 
