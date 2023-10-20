### Why use Spring Batch?
- 단발성으로 대용량의 데이터를 처리하기 위해서
- Spring Batch를 이용해서 배치 어플리케이션 개발에 집중할 수 있다 (마치 Spring MVC를 이용하는 이유)

### Batch Application Requirement
- 대용량 데이터: 대량의 데이터를 가져오거나, 전달하거나, 계산하는 등의 처리가 가능해야 한다.
- 자동화: 심각한 문제 해결을 제외하고는 사용자 개입 없이 실행되어야 한다.
- 견고성: 잘못된 데이터를 충돌/중단 없이 처리할 수 있어야 한다.
- 신뢰성: 무엇이 잘못되었는지 추적할 수 있어야 한다. (로깅, 알림)
- 성능: 지정한 시간 안에 배치 처리를 완료하거나 동시에 실행되는 다른 어플리케이션을 방해하지 않도록 수행되어야 한다.

### About Spring Batch
- Accenture와 Spring Source의 공동 작업으로 2007년에 탄생했다.
- DI, AOP, 서비스 추상화 등 Spring 프레임워크의 3대 요소를 모두 사용할 수 있다.
- Transaction management
- Chunk based processing 
- Declarative I/O
- Start/Stop/Restart
- Retry/Skip
- Web based administration interface ([Spring Cloud Data Flow](https://cloud.spring.io/spring-cloud-dataflow))

### Spring Batch Scenarios
- Commit batch process periodically.
	- 주기적인 배치 프로세스 커밋
- Concurrent batch processing: parallel processing of a job.
	- 동시 배치 작업, 병렬 Job 처리
- Staged, enterprise message-driven processing.
	- 메시지 기반 프로세싱
- Massively parallel batch processing.
	- 대규모 병렬 배치 처리
- Manual or scheduled restart after failure.
	- 실패 후 매뉴얼 또는 스케줄된 재시작
- Sequential processing of dependent steps (with extensions to workflow-driven batches).
	- 각 Step 별 순차 처리
- Partial processing: skip records (for example, on rollback).
	- 부분 처리 (records 스킵 또는 롤백)

### Reference
- https://jojoldu.tistory.com/324?category=902551
- https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#spring-batch-intro
