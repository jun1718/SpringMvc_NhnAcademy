package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.exception.UserNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.validator.StudentRegisterRequestValidator;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller

@RequestMapping("/student/{studentId}")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentRegisterRequestValidator validator;

    public StudentController(StudentRepository studentRepository,
                             StudentRegisterRequestValidator validator) {
        this.studentRepository = studentRepository;
        this.validator = validator;
    }

    @ModelAttribute("studentId")
    public Long getStudentId(@PathVariable Long studentId) {
        if (!studentRepository.exists(studentId)) {
            throw new UserNotFoundException();
        }
        return studentId;
    }

    @ModelAttribute("student")
    public Student getStudent(@ModelAttribute("studentId") Long studentId) {
        return studentRepository.getStudent(studentId);
    }

    @GetMapping(params = {"!hideScore"}) // 사용할 필요 없지만 공부목적으로 사용
    public String viewStudent(@ModelAttribute("studentId") Long studentId, ModelMap modelMap) {
        modelMap.addAttribute("student", studentRepository.getStudent(studentId));

        return "thymeleaf/studentView";
    }

    @GetMapping(params = {"hideScore"})
    public ModelAndView viewStudentHideScore(@ModelAttribute("student") Student student,
                                             @RequestParam(value = "hideScore", required = true) String hideScore) {
        ModelAndView mav = new ModelAndView("thymeleaf/studentView");
        mav.addObject("student", student);
        mav.addObject("hideScore", hideScore);
        return mav;
    }

    @GetMapping("/modify")
    public String studentModifyForm(@ModelAttribute("studentId") Long studentId, Model model) {
        model.addAttribute("student", studentRepository.getStudent(studentId));
        return "thymeleaf/studentModify";
    }

    @PostMapping("/modify")
    public String modifyUser(@ModelAttribute(value = "studentId", binding = false) Long studentId,
                             @Valid @ModelAttribute StudentRegisterRequest studentRegisterRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Student student = studentRepository.getStudent(studentId);
        student.setName(studentRegisterRequest.getName());
        student.setComment(studentRegisterRequest.getComment());
        student.setEmail(studentRegisterRequest.getEmail());
        student.setScore(studentRegisterRequest.getScore());

        return "redirect:/student/" + studentId;
    }

    @ExceptionHandler({ValidationFailedException.class})
    public String handleException(ValidationFailedException ex, Model model) {
        model.addAttribute("exception", ex);
        log.error("", ex);
        return "thymeleaf/error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound() {}

    @InitBinder("studentRegisterRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
}
