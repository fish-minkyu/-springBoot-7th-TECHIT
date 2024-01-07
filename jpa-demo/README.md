# Spring Boot Data JPA

JPA(Hibernate)는 Java를 비롯한 객체지향 언어의 관점에서 관계형 데이터베이스를
사용할 수 있게 해주는 ORM(Object Relational Mapping) 기술의 일종이다.
Spring Boot Data JPA는 해당 기술을 Spring Boot 프로젝트에 쉽게 적용할 수 있게
해주는 Spring Boot의 하위 프로젝트 이다.

JPA 자체는 ORM 기술이 아니다. JPA는 Java 기반의 다른 ORM 기술들이 어떻게 동작할지를
결정하는 기준이 되는 기능 명세에 가깝다. Spring Boot Data JPA에서
실제로 ORM 기술을 구현하는 프레임워크는 Hibernate이다.

## JPA 관련 설정

```yaml
spring:
  datasource:
    url: jdbc:sqlite:db.sqlite
    driver-class-name: org.sqlite.JDBC
    # username: sa
    # password: password
```

기본적인 Database 관련 설정이다. 이는 JPA외의 Spring Boot Data JDBC, Mybatis 등의 기술도 공유한다.
- `spring.datasource.url`: 사용할 데이터베이스의 JDBC URL
- `spring.datasource.driver-class-name`: 사용할 JDBC Driver 클래스
- `spring.datasource.username`: (필요시) 데이터베이스 사용자 아이디
- `spring.datasource.password`: (필요시) 데이터베이스 사용자 비밀번호

```yaml
spring:
  # ...
  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true
    database-platform: org.hibernate.community.dialect.SQLiteDialect
```

JPA에서 활용하는 가장 기본적인 설정이다.
- `spring.jpa.hibernate.ddl-auto`: Hibernate가 DDL을 어떻게 적용할지에 대한 설정.
    - `create`: 이전 데이터를 제거하고, 스키마를 생성한다.
    - `create-drop`: 실행시 스키마를 생성하고, 종료시 스키마를 제거한다.
    - `update`: 필요시 스키마를 갱신한다.
    - `none`: 스키마를 건드리지 않는다.
- `spring.jpa.show-sql`: Hibernate가 생성한 SQL을 콘솔에서 확인할지에 대한 설정.
- `spring.jpa.database-platform`: Hibernate에서 사용할 SQL Dialect.

예시로 주어진 설정은 SQLite를 사용하는 설정이다.

## `@Entity`

데이터베이스에 들어가는 데이터를 클래스의 형태로 표현하고자 한다면,
해당 데이터를 묘사한 클래스에 `@Entity` Annotation을 추가해준다.

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String phone;
    private String email;
    
    // ...
}
```

`@Entity`를 추가하면 반드시 `@Id`가 추가된 속성을 정의해야 한다.
이 속성이 데이터베이스 테이블의 PK를 의미한다.

`ddl-auto` 설정에 따라 이런 `@Entity` 클래스의 설정에 맞도록
스키마가 조작되기도 한다. 예를 들어 `ddl-auto`가 `create`인 경우 아래와
같은 테이블이 데이터베이스에 자동으로 생성된다.

![student 테이블](assets/images/student_table_1.png)

### `@Table`, `@Column`

상황에 따라 실제 클래스의 이름과 데이터베이스 테이블의 이름을 달리 하고 싶을 수 있다.
이때는 `@Table`이나 `@Column` Annotation을 이용하면, 실제 데이터베이스에 반영될
이름을 설정해줄 수 있다. 그 외에 Database Constraints 등을 위해서도 사용한다.

```java
// @Entity 클래스에 작성한다.
// 생성되는 테이블의 이름을 "student"로
@Entity
@Table(name = "student")
public class StudentEntity {
    // ...
    // @Entity 클래스의 속성에 작성한다.
    // Constraint 및 컬럼 이름 지정
    @Column(name = "username", unique = true)
    private String name;
    // ...
}
```

## `JpaRepository<T, ID>`

데이터베이스에 접근하는데 도움을 주는 인터페이스이다. CRUD를 위한 기초 메서드
등이 정의되어 있다. 특정 `@Entity` 클래스를 기준으로 `interface`를 만들고,
`JpaRepository`를 상속받아 사용한다.

```java
public interface StudentRepository
        extends JpaRepository<Student, Long> {}
```

두개의 타입 파라미터를 필요로 하는데, `T`는 대상 `@Entity` 클래스를,
`ID`는 해당 클래스에서 `@Id` 속성의 타입을 전달한다.

이렇게 만들어진 `interface`를 바탕으로 Spring Boot Data JPA는
Bean 객체를 생성한다. 즉, 다른 클래스에서 그 구현체를 주입받아 사용할 수 있다.

```java
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
```

### CREATE

새로운 `@Entity` 인스턴스를 만들고, `save()` 메서드의 인자로 전달하면
새로운 데이터 열이 데이터베이스의 테이블에 생성된다.

```java
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    
    public void createStudent(
            String name,
            Integer age,
            String phone,
            String email
    ) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setPhone(phone);
        student.setEmail(email);
        studentRepository.save(student);
    }

    // ...
}
```

### READ

`findAll()`을 사용하면, 테이블의 모든 데이터를 반환받을 수 있으며,
`findById(ID id)`를 사용하면 해당 PK를 가진 데이터를 반환한다.

```java
    // ...
    public Student readStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> readStudentAll() {
        return studentRepository.findAll();
    }
    // ...
```

### UPDATE

CREATE와 마찬가지로 `save()` 메서드를 사용한다. 단, `save()`와 달리
데이터베이스에서 반환받은 실제 `@Entity` 클래스를 수정 후 전달한다.

```java
    // ...
    public void update(
            Long id,
            String name,
            Integer age,
            String phone,
            String email
    ) {
        Student target
                = studentRepository.findById(id).orElse(null);
        target.setName(name);
        target.setAge(age);
        target.setPhone(phone);
        target.setEmail(email);
        studentRepository.save(target);
    }
    // ...
```

### DELETE

`delete()`를 이용해 실제 `@Entity` 인스턴스를 전달하거나,
`deleteById(ID id)`를 이용해 PK를 기준으로 삭제할 수 있다.

```java
    // ...
    public void deleteStudent(Long id) {
        Student targetEntity = 
                studentRepository.findById(id).orElse(new Student());
        studentRepository.delete(targetEntity);
        // or
        studentRepository.deleteById(id);
    }
}
```

## 기초 관계 설정

아래의 ERD를 확인해보자.

![student - instructor](assets/images/student_instructor.png)

관계를 위한 FK를 제외하고 `@Entity` 클래스를 구성하면 다음과 같다.

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String phone;
    private String email;
}
```

```java
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

}
```

### `@ManyToOne`

FK를 가져야 하는 `Student` 클래스에, `Instructor` 타입의 속성을 추가하고,
`@ManyToOne` Annotation을 추가하면,

```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String phone;
    private String email;

    @ManyToOne
    private Instructor advisor;
}
```

`ddl-auto`로 스키마를 만들 때, 해당 속성이 FK 컬럼으로 변경되어 테이블에 반영된다.

![student 테이블](assets/images/student_table_2.png)

`@Column` Annotation의 역할과 비슷하게 `@JoinColumn`을 사용할 수 있다.

```java
@Entity
public class Student {
    // ...
    @ManyToOne
    @JoinColumn(name = "advisor")
    private Instructor advisor;

}
```

![student 테이블](assets/images/student_table_3.png)

관계를 반영하여 데이터베이스에 저장하고자 한다면, 일반적인 Java 객체를 다루듯
속성에 할당한 뒤, `save()` 메서드에 해당 객체를 전달하면 된다.

```java
public void create(
        String name,
        Integer age,
        String phone,
        String email,
        Long advisorId
) {
    Student student = new Student();
    student.setName(name);
    student.setAge(age);
    student.setPhone(phone);
    student.setEmail(email);
    student.setAdvisor(instructorRepository.findById(advisorId).orElse(null));
    studentRepository.save(student);
}
```

### `@OneToMany`

상황에 따라 참조되는 `@Entity` (지금은 `Instructor`) 쪽에서 자신과 관계를 맺는
데이터를 활용하고 싶을 수 있다. 이때는 `id`를 바탕으로 검색을 하는 방법도 있지만,
`@OneToMany` Annotation을 이용해볼 수 있다.

```java
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "advisor")
    private List<Student> advisingStudents;
}
```

여기서 `mapped`의 경우 참조하는 반대쪽 `@Entity`의 속성의 이름을 전달한다.

데이터를 확인하는 단계에선 받아온 `@Entity`의 속성을 확인하면 된다.

```java
@GetMapping("{id}")
public String readOne(
        @PathVariable("id")
        Long id,
        Model model
) {
    Instructor instructor = instructorService.readInstructor(id);
    model.addAttribute("instructor", instructor);
    model.addAttribute("advisingStudents", instructor.getAdvisingStudents());
    return "instructor/read";
}
```