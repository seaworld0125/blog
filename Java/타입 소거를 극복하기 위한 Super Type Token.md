자바는 제네릭을 이용하지만 제네릭이 없던 시절(자바 1.5 이전)과의 호환성을 위해 타입 소거라는 장치를 두었습니다. 그로 인해 런타임에 제네릭 클래스의 타입을 특정할 수 없는 불상사가 발생합니다.

외부 라이브러리 중에서 Jackson 같은 경우, 역직렬화 같은 연산을 할 때 제네릭 클래스의 타입을 특정할 수 있어야 합니다. 그런데 List와 같은 제네릭 클래스를 이용할 때 문제가 발생합니다. List가 컴파일 이후 비-구체화 타입이 되면서 제네릭 타입 정보를 잃기 때문입니다.

```Java
// before compile
List<String> stringList;

// after compile
List<?> stringList;
```

따라서 Jackson은 데이터가 리스트 형태인 것은 알아도 제네릭 타입을 알 수 없어 역직렬화를 수행할 수 없습니다. Type-Token 기법(타입의 클래스 정보를 넘기는 것)을 이용하는 것도 타입 소거에 의해서 불가능합니다.

Super Type Token은 이를 극복하기 위해 나온 기법입니다. 간단하게 말해서 타입을 전달하면 해당 타입에 대한 정보를 필드로 가지고 있고 런타임에 활용하는 것입니다. 대표적인 예시는 TypeReference 클래스가 있습니다. 코드를 살펴보면 `_type` 필드가 타입에 대한 클래스 정보를 가지고 있음을 알 수 있습니다. 이를 Jackson이 활용하면 런타임에도 역직렬화를 문제없이 수행할 수 있습니다.

```Java
public abstract class TypeReference<T> implements Comparable<TypeReference<T>>  
{  
    protected final Type _type;  
    protected TypeReference()  
    {  
        Type superClass = getClass().getGenericSuperclass();  
        if (superClass instanceof Class<?>) { // sanity check, should never happen  
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");  
        }  
        /* 22-Dec-2008, tatu: Not sure if this case is safe -- I suspect  
         *   it is possible to make it fail?         *   But let's deal with specific         *   case when we know an actual use case, and thereby suitable         *   workarounds for valid case(s) and/or error to throw         *   on invalid one(s).         */        _type = ((ParameterizedType) superClass).getActualTypeArguments()[0];  
    }  
  
    public Type getType() { return _type; }  
    /**  
     * The only reason we define this method (and require implementation     * of <code>Comparable</code>) is to prevent constructing a  
     * reference without type information.     */    @Override  
    public int compareTo(TypeReference<T> o) { return 0; }  
    // just need an implementation, not a good one... hence ^^^  
}
```