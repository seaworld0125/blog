
1. JpaPagingItemReader를 사용
2. 쿼리에 정렬조건이 명시되어 있지 않아서 정렬 조건 문제인 것으로 추측했으나(https://jojoldu.tistory.com/166) 2만건으로 재현해 본 결과 동일한 증상
3. 다시 쿼리를 점검해보니 deleted_at 조건이 문제였음. 실시간으로 처리 후 deleted_at이 갱신되면서 페이징 결과가 달라지는 문제가 있었음
4. 따라서 deleted_at 조건을 제거하고 process 과정에서 filtering 처리할 수 있도록 코드를 수정하였음
5. 테스트 결과 동일한 증상이 재현되지 않음을 확인