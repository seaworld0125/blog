Vue에서 컴포넌트를 중첩으로 사용하고 싶을 때 방법을 소개한다.

아래 컴포넌트 예시를 보자. 컴포넌트의 이름은 CustomTable이다. 자기 자신을 template에서 재귀로 사용하고 싶을 때 name이라는 속성을 통해서 자기 자신을 참조할 수 있다. 참조할 때 kebab case로 참조하면 된다.
```vue.js
// CustomTable.vue

<script>
export default {
	name: "CustomTable",
	...
}
</script>
<template>
	<custom-table/>
</template>
<style>
</style>
```

