### 현상
1만 1천 건의 데이터가 오전 10시에 처리되었어야 했지만, 약 400건의 데이터가 Batch Application에서 읽히지 않는 문제가 발생. BATCH_STEP_EXECUTION을 살펴보았을 때 Read 처리 과정에 문제가 있음을 알 수 있었음.

### 해결 과정
1. JpaPagingItemReader를 사용하고 있었음
2. 쿼리에 정렬 조건이 명시되어 있지 않아서 정렬 조건이 문제인 것으로 예상했으나 쿼리 수정 후 2만 건의 테스트 데이터로 재현해 본 결과 여전히 문제가 재현됨. 그러나 정렬 조건이 없으면 추후 다른 문제(참고: https://jojoldu.tistory.com/166)를 일으킬 수 있었기 때문에 정렬 조건을 추가하는 것으로 결정
3. 쿼리를 다시 점검해보니 deleted_at 조건이 문제였음. 실시간으로 데이터를 처리 후 deleted_at이 갱신되면서 페이징 결과가 달라지는 문제가 있었음
4. 따라서 deleted_at 조건을 제거하고 process 과정에서 deleted_at is not null 일 경우 필터링 하도록 코드를 수정하였음
	1. 필터링된 row의 수는 BATCH_STEP_EXECUTION에 filter_count 컬럼으로 확인할 수 있음
5. 다시 2만 건의 테스트 데이터로 재현해 본 결과 동일한 증상이 재현되지 않음을 확인 하였음