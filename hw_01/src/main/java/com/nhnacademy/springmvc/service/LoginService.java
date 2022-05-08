package com.nhnacademy.springmvc.service;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.repository.StudentRepository;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final StudentRepository studentRepository;

    public LoginService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean check(Long id, String pw) {
        Student student = studentRepository.getStudent(id);
        if (Objects.isNull(student)) return false;
        if (!Objects.equals(student.getPassword(), pw)) return false;
        return true;
    }
}
