package com.example.CRUD.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data Transfer Object
// Value Object
// Data Object
// 학생의 데이터를 담기 위한 클래스다.

// @Data == Getter + Setter + EqualAndHashCode + etc
// : 기능 범위가 넓어서 쓰는 걸 지양하지만 롬복을 쓰면 거기서 거기다.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
  // 데이터베이스의 PK
  private Long id;
  // 이름 정보
  private String name;
  // 이메일
  private String email;
}
