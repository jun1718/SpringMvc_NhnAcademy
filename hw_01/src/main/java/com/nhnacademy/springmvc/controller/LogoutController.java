package com.nhnacademy.springmvc.controller;

import java.util.Objects;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/logout")
public class LogoutController {
    @GetMapping
    public String logout(HttpSession session) {
        if (Objects.nonNull(session)) { // 필터에서 걸러주기때문에 사실 이조건은 필요없음 하지만 뭔가모를 불확실성에 대비하여 이중처리
            session.invalidate();
        }

        return "redirect:/";
    }
}
