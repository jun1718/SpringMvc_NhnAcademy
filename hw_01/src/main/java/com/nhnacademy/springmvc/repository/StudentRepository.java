package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public interface StudentRepository {
    boolean exists(long id);

    Student register(String password, String name, String email, int score, String comment);

    Student getStudent(long id);

    Student modify(long id, StudentRegisterRequest studentRegisterRequest);
}
