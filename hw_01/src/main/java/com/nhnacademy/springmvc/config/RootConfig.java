package com.nhnacademy.springmvc.config;

import com.nhnacademy.springmvc.Base;
import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.repository.StudentRepositoryImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = Base.class,
excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootConfig {
    @Bean
    public StudentRepository student() {
        StudentRepository studentRepository = new StudentRepositoryImpl();
        studentRepository.register("12345", "admin", "admin@naver.com", 100, "관리자는 백점이죠.");
        return studentRepository;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("message");

        return messageSource;
    }
}
