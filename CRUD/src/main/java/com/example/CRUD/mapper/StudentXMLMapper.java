package com.example.CRUD.mapper;

import com.example.CRUD.model.StudentDto;

import java.util.List;

public interface StudentXMLMapper {
  List<StudentDto> selectStudentAll();
  StudentDto selectStudent(Long id);
  void insertStudent(StudentDto dto);
}
