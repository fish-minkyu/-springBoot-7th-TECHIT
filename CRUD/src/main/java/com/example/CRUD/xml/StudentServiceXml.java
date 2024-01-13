package com.example.CRUD.xml;

import com.example.CRUD.model.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceXml {

  public final StudentXMLDao xmlDao;

  public void createStudent(String name, String email) {
    StudentDto dto = new StudentDto();
    dto.setName(name);
    dto.setEmail(email);
    xmlDao.createStudent(dto);
  }

  // 현재 등록된 모든 학생을 반환한다.
  public List<StudentDto> readStudentAll() {
    return xmlDao.readStudentsAll();
  }

  public StudentDto readStudent(Long id) {
    return xmlDao.resdStudent(id);
  }

  // 어떤 학생의 정보를 바꿀건지를 나타내는 id
  // 그 학생의 새로운 정보 name, email
  public StudentDto updateStudent(Long id, String name, String email) {
    // TODO StudentDao를 사용하게 변경
    StudentDto dto = new StudentDto();
    dto.setName(name);
    dto.setEmail(email);
    xmlDao.updateStudent(dto);

    return xmlDao.resdStudent(id);
  }



  // id를 바탕으로 학생을 제거하는 메서드
   public void deleteStudent(Long id) {
    // TODO StudentDao를 사용하게 변경
     xmlDao.deleteStudent(id);
  }
}
