package com.nhnacademy.springmvc.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Setter;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Data
public class StudentRegisterRequest {
//    @NotBlank
    private String name;
//    @Email
    private String email;
//    @Length(min = 0, max = 100)
//    @Min(0) @Max(100)
    private int score;

    private String comment;
    private String password;

}
