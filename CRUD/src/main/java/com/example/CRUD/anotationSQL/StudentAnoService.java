package com.example.CRUD.anotationSQL;

import com.example.CRUD.model.StudentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentAnoService {
  // StudentDao(MyBatis)를 추가해주고
  private final StudentAnoDao dao;

  // 주입받자
  public StudentAnoService(StudentAnoDao dao) {
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
  // 그 학생의 새로운 정보 name, age, phone, email
  public StudentDto updateStudent(Long id, String name, String email) {
    // TODO StudentDao를 사용하게 변경
    StudentDto student = dao.readStudent(id);
    student.setName(name);
    student.setEmail(email);
    System.out.println(student);

    dao.updateStudent(student);
    return readStudent(id);
  }



  // id를 바탕으로 학생을 제거하는 메서드
  public void deleteStudent(Long id) {
    // TODO StudentDao를 사용하게 변경
    dao.deleteStudent(id);
  }
}
