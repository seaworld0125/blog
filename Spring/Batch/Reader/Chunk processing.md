- 데이터를 Chunk(덩어리) 단위로 처리하는 것을 Chunk processing 이라고 한다.
- 서버의 물리적 한계가 있기 때문에 대량의 데이터를 처리할 때는 무조건 Chunk processing으로 진행해야 한다.

### Pagination
데이터를 Page 단위로 나누어 처리하는 방식. limit, offset을 이용한다. 
#### MySQL Limit