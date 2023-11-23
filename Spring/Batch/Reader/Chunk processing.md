데이터를 Chunk(덩어리) 단위로 처리하는 것을 Chunk processing 이라고 합니다. 서버의 물리적 한계가 있기 때문에 대량의 데이터를 처리할 때는 무조건 Chunk processing으로 진행해야 합니다.

### Pagination
데이터를 Page 단위로 나누어 처리하는 방식입니다. limit, offset을 이용합니다. 치명적인 단점은 offset 만큼 불필요한 데이터를 읽어들이고 건너뛰어야 합니다. 즉 offset이 증가할수록 성능 이슈가 발생합니다.

이러한 성능 이슈를 해결하기 위해서 Zero offset 방식을 이용할 수 있습니다. where 절에 항상 마지막 pk부터 조회하도록 하여 항상 offset 만큼의 불필요한 데이터를 건너뛰지 않아도 되는 방식입니다.

```sql
first: select * t1 where id > 0 and limit 100
second: select * t1 where id > 100 and limit 100
third: select * t1 where id > 200 and limit 100
...
```

Pagination 방식을 이용하는 PageItemReader는 아래와 같이 두 가지가 있습니다.
1. **RepositoryItemReader**
2. **JpaPagingItemReader**

### Cursor
Cursor를 이용해서 데이터가 없을 때까지 반복해서 fetch 하는 방식입니다. 이를 이용하는 CursorItemReader는 아래와 같이 세 가지가 있습니다.
1. **JpaCursorItemReader**
2. **JdbcCursorItemReader**
3. **HibernateCursorItemReader**

**JpaCursorItemReader**는 올바른 Cursor 방식을 이용하지 않기 때문에 주의해야 합니다. DB에서 모든 데이터를 읽고 Iterator로 Cursor를 흉내내는 방식이기 때문에 OOM을 유발합니다.
**JdbcCursorItemReader**의 경우 Native sql로 구현해야 하고 **HibernateCursorItemReader**의 경우 HQL로 구현해야 하기 때문에 불편하다는 단점이 있습니다.

### 성능측정
(참고) https://tech.kakaopay.com/post/ifkakao2022-batch-performance-read/#%EC%84%B1%EB%8A%A5-%EC%B8%A1%EC%A0%95

### Ref
https://tech.kakaopay.com/post/ifkakao2022-batch-performance-read/#%EC%84%B1%EB%8A%A5-%EC%B8%A1%EC%A0%95
