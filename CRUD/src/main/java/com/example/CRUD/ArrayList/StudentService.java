package com.example.CRUD.ArrayList;

import com.example.CRUD.model.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
  // 현재 몇번째 데이터가 입력되었는지 확인하기 위한 속성
  // 나중에 데이터베이스의 PK역할
  private Long nextId = 1L;

  // 데이터를 담기 위한 리스트
  private final List<StudentDto> studentList = new ArrayList<>();

  // Create
  // 사용자의 데이터를 받아서 새로운 학생 객체를 생성해 리스트에 저장한다.
  public StudentDto createStudent(String name, String email) {
  StudentDto newStudent = new StudentDto(nextId, name, email);
  nextId++;
  studentList.add(newStudent);
  return newStudent;
  }

  // Read
  // 현재 등록된 모든 학생을 반환한다.
  public List<StudentDto> readStudentAll() {
      return studentList;
  }

  // 단일 학생 리턴(PK인 id로 조회하는 것이 권장됨)
  // id를 받아서 하나의 학생 데이터를 반환한다.
  public StudentDto readStudent(Long id) {
    // studentList의 데이터를 하나씩 확인해서
    // getId가 id인 데이터를 반환하고,
    for (StudentDto studentDto : studentList) {
      if (studentDto.getId().equals(id)) {
        return studentDto;
      }
    }
    // 없을 경우 null을 반환한다.
    return null;
  }

  // Update
  // 어떤 학생의 정보를 바꿀건지를 나타내는 id
  // 그 학생의 새로운 정보 name, email
  public StudentDto updateStudent(Long id, String name, String email) {
    for (StudentDto studentDto : studentList) {
      if (studentDto.getId().equals(id)) {
        studentDto.setName(name);
        studentDto.setEmail(email);
        return studentDto;
      }
    }

    return null;
  }

  // Delete
  // id를 바탕으로 학생을 제거하는 메서드
  public void deleteStudent(Long id) {
    System.out.println("delete에 도착함");
    // 몇번째 원소를 제거하면 되는지
    int target = -1;
    // 리스트의 각 원소를 확인하면서 주어진 id와 일치하는 원소가 있으면, 그 index를 저장
    for (int i = 0; i < studentList.size(); i++) {
      if (studentList.get(i).getId().equals(id)) {
        target = i;
        break;
      }
    }

    // 그 index의 위치에 있는 데이터를 studentList에서 제거
    if (target != -1) {
      studentList.remove(target);
    }
  }


}
