package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
/*
CREATE TABLE student(
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  NAME VARCHAR(255),
  AGE INTEGER,
  PHONE VARCHAR(255),
  EMAIL VARCHAR(255)
);
*/

@Data // Data 어노테이션은 나중에 가선 사용하지 않을 것이다.
@Entity
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Integer age;
  private String phone;
  private String email;
  @ManyToOne // Student Entity에다가 Instructor Entity를 넣는 것이다.
  // FK Column -> Join Column, FK 컬럼을 Join 컬럼이라고 부른다.
//  @JoinColumn(name = "advisor")
  private Instructor advisor; // advisor이라 적어도 JPA에서 알아서 advisor_id라고 바꾼다.
}
