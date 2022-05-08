package com.nhnacademy.springmvc.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class LoginRequest {
    @Min(1)
    private Long id;
    @NotBlank
    private String pw;
}
