package com.example.jpa;

import com.example.jpa.dto.InstructorDto;
import com.example.jpa.entity.Instructor;
import com.example.jpa.repo.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public Instructor readInstructor(Long id) {
        return instructorRepository.findById(id)
          .orElse(null);
    }

    // 반환 타입이 List<InstructorDto>가 아닌 List<Instructor>인 이유
    // : 서비스 계층에서는 데이터 접근 계층에서 직접 가져온 도메인 모델 객체를 반환하는 것이 좋은 습관이기 때문이다.
    // 비즈니스 로직이 처리되는 서비스 계층에서 원시 데이터를 그대로 사용하는 것이 좋은데 비즈니스 로직 처리에 필요한 모든 데이터를 활용할 수 있기 때문
    public List<Instructor> readInstructorAll() {
        return instructorRepository.findAll();
    }
}
