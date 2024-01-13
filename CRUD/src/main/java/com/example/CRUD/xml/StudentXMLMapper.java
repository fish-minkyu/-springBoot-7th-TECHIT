package com.example.CRUD.xml;

import com.example.CRUD.model.StudentDto;

import java.util.List;

public interface StudentXMLMapper {
  List<StudentDto> selectStudentAll();
  StudentDto selectStudent(Long id);
  void insertStudent(StudentDto dto);
  void updateStudent(StudentDto dto);
  void deleteStudent(Long id);
}
