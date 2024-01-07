package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Instructor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String firstName;
  private String lastName;

  @OneToMany(mappedBy = "advisor") // Student 엔티티의 advisor 컬럼을 지칭
  private List<Student> advisingStudents;
}
