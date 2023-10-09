![[Pasted image 20231009212628.png]]
- 생성자 주입이 되지 않는다.
- `@Autowired` 를 명시적으로 사용해야 `Jupiter`가 `Spring Container`에 빈 주입 요청을 할 수 있다.
- 테스트 프레임워크에서 프레임워크의 주체는 `Jupiter`이기 때문에 아무리 생성자 주입이라 한들 `@Autowired` 애노테이션이 명시되지 않은 객체는 의존성 주입을 받을 수 없게 되는 것이다.

1. `@Autowired`는 `BeanPostProcessor`라는 라이프 사이클 인터페이스의 구현체인 `AutowiredAnnotationBeanPostProcessor`에 의해 의존성 주입이 이루어진다.
		1.만약 없다면? Jupiter는 JUnit의 Parameter Resolver를 찾아보겠지만, 우린 구현한적 없으니 제대로 동작할 수가 없다. https://www.baeldung.com/junit-5-parameters
1. `BeanPostProcessor`는 초기화 라이프 사이클 이전과 이후에 필요한 부가 작업을 할 수 있는 라이프 사이클 콜백이다. 즉, 빈이 만들어지는 시점 이전 혹은 이후에 추가적인 작업을 하고 싶을 때 사용된다.
2. `AutowiredAnnotationBeanPostProcessor`가 빈초기화 라이프 사이클 이전(Bean 인스턴스 생성 이전)에 `@Autowired`가 붙어 있는 Bean을 찾아 의존성 주입을 수행해준다.

reference
- https://minkukjo.github.io/framework/2020/06/28/JUnit-23/