package com.example.CRUD.mapper;

import com.example.CRUD.StudentDao;
import com.example.CRUD.model.StudentDto;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper // Mybatis가 SQL을 날리기 위한 매개체로 활용
public interface StudentMapper {
  // MyBatis는 interface의 메서드에 SQL을 연결시킨다.
  // MyBatis의 세션을 이용해서 interface의 메서드를 실행하면 연결된 SQL이 실행된다.
  @Select("SELECT * FROM student;")
  List<StudentDto> selectStudentAll(); // 호출 시 SQL 실

  // SELECT, INSERT, UPDATE, DELETE 다 있다.

  // Insert
  @Insert("INSERT INTO student(name, email) " +
  "VALUES(#{name}, #{email});")
  void insertStudent(StudentDto dto);

  // Select 하나
  @Select("SELECT * FROM student WHERE id = #{id};")
  StudentDto selectStudent(Long id);
  // Optional 사용
//  Optional<StudentDto> selectStudent(Long id);

  @Update("UPDATE student SET " +
    "name = #{name}, " +
    "email = #{email} " +
    "WHERE id = #{id}")
  void updateStudent(StudentDto dto);

  @Delete("DELETE FROM student " +
    "WHERE id = #{id}")
  void deleteStudent(Long id);
}
