package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Student;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    Map<Long, Student> students = new LinkedHashMap<>();
    long id = 0;

    @Override
    public boolean exists(long id) {
        return Objects.nonNull(students.get(id));
    }

    @Override
    public Student register(String name, String email, int score, String comment) {
        id++;
        Student student = new Student(id, name, email, score, comment);
        students.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(long id) {
        return students.get(id);
    }

    public Map<Long, Student> getStudents() {
        return students;
    }
}
