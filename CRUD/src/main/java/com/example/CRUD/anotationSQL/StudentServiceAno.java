package com.example.CRUD.anotationSQL;

import com.example.CRUD.model.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceAno {
  // StudentDao(MyBatis)를 추가해주고
  private final StudentDao dao;

  // 주입받자
  public StudentServiceAno(StudentDao dao) {
    this.dao = dao;
  }

  // Create
  public void createStudent(String name, String email) {
    StudentDto dto = new StudentDto();
    dto.setName(name);
    dto.setEmail(email);
    dao.createStudent(dto);
  }

  // Read
  // 현재 등록된 모든 학생을 반환한다.
  public List<StudentDto> readStudentAll() {
    return dao.readStudentAll();
  }

  // 학생 상세보기
  public StudentDto readStudent(Long id) {
    return dao.readStudent(id);
  }

  // 어떤 학생의 정보를 바꿀건지를 나타내는 id
  // 그 학생의 새로운 정보 name, email
/*  public StudentDto updateStudent(Long id, String name, String email) {
    // TODO StudentDao를 사용하게 변경
  }*/



  // id를 바탕으로 학생을 제거하는 메서드
/*  public void deleteStudent(Long id) {
    // TODO StudentDao를 사용하게 변경
  }*/
}
