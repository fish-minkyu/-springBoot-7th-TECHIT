# demo 프로젝트
- 2024.01.03 `7주차`
  Client-Server Model과 기초 Spring Boot을 연습한 프로젝트다.  
  해당 프로젝트에서, DI 주입, IoC 컨테이너 Model 객체 등등을 연습했다.

## 스택
- Spring Boot 3.2.1
- Spring Web
- thymeleaf

## Key Point
1. `RequestMapping & @RequestParam`
[FormController](/src/main/java/com/example/demo/form/FormController.java)
```java
@Controller
public class FormController {
  // 1. 사용자에게 표기하고 싶은 메시지를 전달할 수 있는 HTML을 반환하는 메서드
  /* @RequestMapping("/send") */
  @GetMapping("/send")
  public String getForm() {
    return "send";
  }

  // 2. 사용자가 전달한 데이터를 처리할 수 있는 메서드
  /*
  @RequestMapping(
    value = "/receive",
    // method 인자로 들어오는 요청의 http method로 한정시킬 수 있다.
    method = RequestMethod.POST
  )
  */
  // PostMapping은 명시적으로 POST 요청만 처리한다.
  @PostMapping("/receive")
  public String receiveData(
    // @RequestParam: 사용자가 보낸 요청의 데이터를 받는 목적의 매개변수를 표기
    @RequestParam("message") String message, // input 태그의 name 속성명을 가지고 온다.
    Model model
  ) {
    System.out.println(message);
    model.addAttribute("message", message);
    return "receive";
  }
}
```

2. `thymeleaf view 반환 & Model 객체 설명`  
[MvcController](/src/main/java/com/example/demo/thyme/MvcController.java)
```java
  // /text로 요청이 오면 text.html을 반환하는 메서드
  @RequestMapping("/text")
  // Model은 View에 사용할 데이터를 모아두는 객체
  public String text(Model model) {
    model.addAttribute(
      "message",
      "Hello Thymeleaf!"
    );
    return "text"; // .html 확장자가 생략해도 됨
  }
```

3. `DI 주입 - 생성자 주입`  
[DemoController]()
```java
@Controller // Spring boot가 @Controller 어노테이션을 붙은 클래스는 어떠한 방식으로 동작하는구나 라고 알 수 있다.
public class DemoController {
  // DemoController는 HelloComponent를 사용한다.
  // DemoController는 HelloComponent가 있어야한다고 명시
  // DemoController는 HelloComponent를 필요로 한다.(의존성)
  // Composition: 구성
  private HelloComponent component;

  // 생성자
  // : 이 객체를 필요로 할 때, 만들어지는 코드
  // Spring IoC 컨테이너가 DemoController가 HelloComponent를 필요로 할 때
  // 알아서 해당 객체를 만들어준다.
  public DemoController(HelloComponent component) {
    this.component = component;
  }
}
```

4. `Bean 객체 확인`  
[DemoApplication]()
```java
		// 어떤 클래스들이 Bean으로 등록이 되었는지 살펴보기 위한 코드
		// 현재 실행중인 IoC Container를 변환한다.
		ApplicationContext applicationContext // 컨텍스트는 맥락이란 뜻
			= SpringApplication.run(DemoApplication.class, args);

		// IoC Container가 관리하고 있는 Bean 객체들을 확인한다.
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanName); // Bean 객체들 이름들만 출력
			// demoApplication
			// demoController
			// 이 2개의 클래스가 bean으로 등록이 되어 사용할 수 있는 상태가 된 것을 알 수가 있다.
		}
```

5. `thymeleaf문법, if-unless문, each문`  
[each.html]()
```html
<div th:if="${studentList.isEmpty()}">
  <p>No Students Here...</p>
</div>
<!-- 한명이라도 student가 있는 경우 -->
<div
        th:unless="${studentList.isEmpty()}"
        th:each="student: ${studentList}"
>
  <hr>
  <p>이름: [[${student.name}]]</p>
  <p>이메일: [[${student.email}]]</p>
</div>
```

6. `요소 전체가 아닌 일부 부분만 동적으로 변경하기`  
[text-object.html]()
```html
<!-- 두개의 p요소로 이름과 이메일을 나타내기-->
<!-- public || getter가 있을 때, 객체.속성으로 접근할 수 있다.-->
<p>이름: [[${object.name}]]</p>
<!-- Getter가 존재하는 속성 또는 메서드 사용 가능-->
<p>email: [[${object.getEmail()}]]</p>
```

7. `form`  
[send.html]()
```html
<!--
  action: Form이 데이터를 보낼 URL
  => 어디에 요청을 보낼지 담을 부분
-->
<!--
  method: 어떤 요청을 보낼지
  (데이터를 조회하는(get) 또는 전송하는(post))
-->

<!--HTTP GET: 데이터를 조회하는 요청에 적용하는 메서드-->
<!--HTTP POST: 데이터를 보내는 요청에 적용하는 메서드-->
<!--HTTP 메소드는 자바의 메소드와는 다른 용어다.-->
<form action="/receive" method="post">
  <label for="message-input">Message:
    <input type="text" id="message-input" name="message">
  </label><br>
  <!-- 양식 제출 버튼 -->
  <input type="submit" value="Send Message">
</form>
```

## 복습
~~2024.01.03 복습 완료~~