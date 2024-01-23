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

    // 반환 타입은 List<InstructorDto>로 해야하는 것이 맞다.
    // : 단 이번 프로젝트에선 서비스 규모가 작으므로 entity로 반환을 해준다.
    // 저장되는 데이터 형식과 반환되는 데이터 형식은 달라야 하는데 그 이유는 사용자에게 전체 데이터를 공개하면 안되기 때문이다.
    public List<Instructor> readInstructorAll() {
        return instructorRepository.findAll();
    }
}
