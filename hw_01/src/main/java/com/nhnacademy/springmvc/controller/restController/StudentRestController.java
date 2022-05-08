package com.nhnacademy.springmvc.controller.restController;

import com.nhnacademy.springmvc.domain.Student;
import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.StudentRepository;
import com.nhnacademy.springmvc.validator.StudentRegisterRequestValidator;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/studentsRest")
public class StudentRestController {
    private final StudentRepository studentRepository;
    private final StudentRegisterRequestValidator validator;

    public StudentRestController(
        StudentRepository studentRepository,
        StudentRegisterRequestValidator validator) {
        this.studentRepository = studentRepository;
        this.validator = validator;
    }

    @ModelAttribute("student")
    public Student getStudent(@PathVariable(value = "studentId", required = false) Long studentId) {
        studentId = Optional.ofNullable(studentId).orElse(0L);
        if (Objects.equals(studentId, 0L)) {
            return null;
        }

        return studentRepository.getStudent(studentId);
    }

    @PostMapping // 등록
    @ResponseStatus(HttpStatus.CREATED)
    public Student registerStudent(@Valid @RequestBody StudentRegisterRequest studentRegister,
                                   BindingResult bindingResult) {
        System.out.println("-=================================");
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        Student student = studentRepository.register(studentRegister.getPassword(), studentRegister.getName(), studentRegister.getEmail(),
            studentRegister.getScore(), studentRegister.getComment());

        return student;
    }

//    @GetMapping("/studentsRest/{studentId}") // 200 만 나감 아래 entity로 response값 조작
//    public Student addStudent(@PathVariable long studentId) {
//        Student student = studentRepository.getStudent(studentId);
//        return student;
//    }
    @GetMapping("/{studentId}") // 조회
    public ResponseEntity<Student> viewStudent(@ModelAttribute("student") Student student) {
        return ResponseEntity.status(201).body(student);
    }

    @PutMapping("/{studentId}") // 수정
    public ResponseEntity<Student> modifyStudent(@PathVariable Long studentId,
                                                 @Valid @RequestBody StudentRegisterRequest studentRegister,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        Student student = studentRepository.modify(studentId, studentRegister);

        return ResponseEntity.ok().body(student);
    }

    @InitBinder("studentRegisterRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

//    @ResponseBody
//    @GetMapping(params = {"!hideScore"}) // 사용할 필요 없지만 공부목적으로 사용
//    public ResponseEntity<Student> viewStudent(@ModelAttribute("studentId") Long studentId, ModelMap modelMap) {
//        modelMap.addAttribute("student", studentRepository.getStudent(studentId));
////        return "thymeleaf/studentView";
//        return ResponseEntity.ok()
//            .body(studentRepository.getStudent(studentId));
//    }
}
