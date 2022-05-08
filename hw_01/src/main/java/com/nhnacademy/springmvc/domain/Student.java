package com.nhnacademy.springmvc.domain;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student {
    private Long id;
    private String name;
    private String email;
    private int score;
    private String comment;

    private String password;

    public Student(String password, String name, String email, int score, String comment) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.score = score;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", score=" + score +
            ", comment='" + comment + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}