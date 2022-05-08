package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.validator.StudentRegisterRequestValidator;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {
    private final StudentRepository studentRepository;
    private final StudentRegisterRequestValidator validator;

    public StudentRegisterController(StudentRepository studentRepository,
                                     StudentRegisterRequestValidator validator) {
        this.studentRepository = studentRepository;
        this.validator = validator;
    }

    @GetMapping
    public String studentRegisterForm() {
        return "thymeleaf/studentRegister";
    }

    @PostMapping
    public ModelAndView registerStudent(@Valid @ModelAttribute StudentRegisterRequest registerRequest,
                                        BindingResult bindingResult) {
        Student student = studentRepository.register(registerRequest.getPassword(), registerRequest.getName(), registerRequest.getEmail(),
            registerRequest.getScore(), registerRequest.getComment());
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        ModelAndView mav = new ModelAndView("redirect:/student/" + student.getId());
        return mav;
    }

    @InitBinder("studentRegisterRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
}
