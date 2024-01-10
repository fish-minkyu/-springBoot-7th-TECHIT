package com.example.CRUD.xml;

import com.example.CRUD.model.StudentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// @Slf4j /* <- 롬복으로 만드는 Logger */
@Controller
public class StudentControllerXml {
//  private static final Logger log
//    = LoggerFactory.getLogger(StudentController.class);

  // 필드 주입은 다른 상황, 캐스팅 목적에서 에러가 발생할 수 있어 권장하지 않는다.
  private StudentServiceXml service;

  // @Autowired는 예전엔 항상 쓰는거였지만 요즘 안써도 잘 정상적으로 작동한다. (없어도 됨)
  // 컴파일러 단위에서 무시되는 어노테이션이다.
  // Autowired는 자동 주입이 되는 객체다라는 걸 표현하기 위해 쓰기도 한다.(기능적으로 큰 의미는 없음)
  // 또한, DI할때 생성자가 하나일경우에만 Autowired 생략가능하다
  public StudentControllerXml(StudentServiceXml studentService) {
    this.service = studentService;
  }

  // create-view로 요청이 왔을 때
  // create.html을 반환하는 메서드
//  @RequestMapping("/create-view")
  @GetMapping("/create-view")
  public String createView() {
    return "create";
  }

  // create로 이름과 이메일 데이터를 보내는 요청을 받는 메서드
  @PostMapping("/create")
  public String create(
    @RequestParam("name") String name,
    @RequestParam("email") String email
  ) {
//    log.info(name);
//    log.info(email);
//    StudentDto student = service.createStudent(name, email);
//    log.info(student.toString());

    service.createStudent(name, email);
    // redirect: 어디에 갔더니 다른 곳으로 가세요.
    return "redirect:/home";
  }

  // /home으로 요청을 받으면 home.html에 studentList를 포함해 변환하는 메서드
  @GetMapping("/home")
  public String home(Model model) {
    model.addAttribute("studentList", service.readStudentAll());
    return "home";
  }

  // /read로 요청을 받으면
  // read.html에 student 정보를 포함해 반환하는 메서드
  // Mapping에 {}을 넣으면 그 안에 들어가 있는 데이터를 매개변수에 할당해 줄 수 있다.
  @GetMapping("/read/{id}")
  public String readOne(
    @PathVariable("id") Long id,
    Model model
    ) {
    StudentDto dto = service.readStudent(id);
    model.addAttribute("student", dto);
    return "read";
  }

/*  // Update
  // /update-view/{id}로 요청을 받으면 update.html에 student 정보를 포함해 반환하는 메서드
  // {id}는 path variable이다.
  @GetMapping("/update-view/{id}")
  public String updateView(
    @PathVariable("id") Long id,
    Model model
  ) {
    StudentDto dto = service.readStudent(id);
    model.addAttribute("student", dto);
    return "update";
  }

  @PostMapping("/update/{id}")
  public String update(
    @PathVariable("id") Long id,
    @RequestParam("name") String name,
    @RequestParam("email") String email
  ) {
    StudentDto dto = service.updateStudent(id, name, email);
    return String.format("redirect:/read/%s", dto.getId());
  }

  // Delete
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable("id") Long id) {
    service.deleteStudent(id);
    return "redirect:/home";
  }*/
}