package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Repository;

public class StudentRepositoryImpl implements StudentRepository {
    Map<Long, Student> students = new LinkedHashMap<>();
    long id = 0;

    @Override
    public boolean exists(long id) {
        return Objects.nonNull(students.get(id));
    }

    @Override
    public Student register(String password, String name, String email, int score, String comment) {
        id++;
        Student student = new Student(password, name, email, score, comment);
        students.put(id, student);
        student.setId(id);
        return student;
    }

    @Override
    public Student modify(long id, StudentRegisterRequest studentRegisterRequest) {
        Student student = this.getStudent(id);

        student.setPassword(studentRegisterRequest.getPassword());
        student.setName(studentRegisterRequest.getName());
        student.setEmail(studentRegisterRequest.getEmail());
        student.setScore(studentRegisterRequest.getScore());
        student.setComment(studentRegisterRequest.getComment());

        return student;
    }

    @Override
    public Student getStudent(long id) {
        Student student = students.get(id);
        if (Objects.isNull(student)) {
            throw new IllegalArgumentException(id +"인 id는 존재하지 않습니다.");
        }
        return student;
    }

    public Map<Long, Student> getStudents() {
        return students;
    }
}
