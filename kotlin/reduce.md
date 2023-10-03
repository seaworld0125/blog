### reduce 함수를 사용해 축약하기
- 비어 있지 않은 컬렉션의 축약하지만, 초기값을 설정하지 않는다.
- fold 대신 reduce 연산을 사용한다.
- fold와 사용 목적은 같다.
- 초기값은 컬렉션의 첫 번째 값으로 초기화된다.

```kotlin
fun sum(vararg nums: Int) = nums.reduce {acc, i -> acc + i}
```

