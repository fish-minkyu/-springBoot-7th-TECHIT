package com.example.demo.thyme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

// thymeleaf - 0103 13:00, 14:00 강의

// 사용자의 요청을 받기 위한 controller
@Controller
public class MvcController {
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

  // /text-object로 요청이 오면 text-object.html을 반환하는 메서드
  @RequestMapping("/text-object")
  public String textObject(Model model) {
    model.addAttribute("object",
      new Student("Alex", "alex@gmail.com")
    );
    return "text-object";
  }

  // /is-logged-in으로 요청이 오면 if-unless.html을 반환하는 메서드
  @RequestMapping("/is-logged-in")
  public String isLoggedIn(Model model) {
    model.addAttribute("isLoggedIn", true);
    return "if-unless";
  }

  // /each로 요청이 오면 each.html을 반환하는 메서드
  @RequestMapping("/each")
  public String each(Model model) {
    // 여러 데이터를 가진 객체를 model에 전달
    List<String> listOfStrings = new ArrayList<>();
    listOfStrings.add("Alex");
    listOfStrings.add("Brad");
    listOfStrings.add("Chad");
    model.addAttribute("itemList", listOfStrings);

    // 여러 학생(student) 데이터를 담은 리스트를 model에 전달
    List<Student> studentList = new ArrayList<>();
    studentList.add(new Student("Alex", "alex@gmail.com"));
    studentList.add(new Student("Brad", "brad@gmail.com"));
    model.addAttribute("studentList", studentList);
    return "each";
  }
}
