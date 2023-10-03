#### 알고리즘에서 fold 사용하기
- fold 함수를 사용해 시퀸스나 컬렉션을 하나의 값으로 축약시킨다.
- fold 함수는 배열 또는 반복 가능한 컬렉션에 적용할 수 있는 축약 연산이다.

1. 합 구하기
```kotlin
fun sum(vararg nums: Int) = nums.fold(0) {acc, n ->
	println("acc = $acc, n = $n")
	acc + n
}
```

2. 팩토리얼
```kotlin
fun factorial(n: Long): BigInteger =
	when (n) {
		0L, 1L -> BigInteger.ONE
		else -> (2..n).fold(BigInteger.ONE) {acc, i -> 
			acc * BigInteger.valuOf(i)
		}
	}
```

3. 피보나치
```kotlin
fun fibonaci(n: Int) = (2 until n).fold(1 to 1) {(prev, curr), _ -> 
	curr to (prev + curr)}.second
}
```
