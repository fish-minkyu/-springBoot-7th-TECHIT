package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// @Controller
// : URL에 따른 요청을 처리하는 메서드를 담아두는 클래스임을 나타낸다.
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

  // @RequestMapping
  // : 어떤 URL 요청에 대하여 실행되는 메서드임을 나타낸다.
  @RequestMapping("home")
  public String home() {
    component.sayHello(); // HelloComponent가 없으면 NullPointException이 발생
                          // 그 이유는 객체가 생성되지 않았는데 접근해서 오류가 발생하기 때문이다.
    return "home.html";
  }

  // /profile로 요청이 들어올 때
  // profile.html로 응답하고 싶다.
  @RequestMapping("profile")
  public String profile() {
    return "profile.html";
  }

  // /hobbies로 요청이 들어오면
  // hobbies.html을 응답한다.
  @RequestMapping("hobbies")
  public String hobbies() {
    return "hobbies.html";
  }
}
