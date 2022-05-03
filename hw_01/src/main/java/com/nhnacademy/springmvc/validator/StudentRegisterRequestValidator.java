package com.nhnacademy.springmvc.validator;

import com.nhnacademy.springmvc.domain.StudentRegisterRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class StudentRegisterRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return StudentRegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudentRegisterRequest request = (StudentRegisterRequest) target;
        if (request.getName().trim().length() <= 0) {
            errors.rejectValue("name", "", "name is not null");
        }

        Pattern pattern = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$");
        Matcher match;
        match = pattern.matcher(request.getEmail());

        if (!match.find()) {
            errors.rejectValue("email", "", "invalid email format");
//            return; 한국식 유효성 검증방식(빨리빨리)
        }

        int score = request.getScore();
        if (score < 0 || score > 100) {
            errors.rejectValue("score", "", "score scope is 0 ~ 100");
//            return;
        }


        int length = request.getComment().trim().length();
        if (length <= 0 || length > 200) {
            errors.rejectValue("comment", "", "coment scope is 1 ~ 200");
//            return;
        }
    }
}
