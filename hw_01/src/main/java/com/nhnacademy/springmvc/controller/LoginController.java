package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.LoginRequest;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.service.LoginService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/student/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String login(HttpServletRequest request,
                        Model model) {
        HttpSession session = request.getSession(false);
        if (Objects.isNull(session)) return "thymeleaf/loginForm";

        Long userId = (Long) session.getAttribute("userId");
        if (Objects.isNull(userId)) return "thymeleaf/loginForm";

        model.addAttribute("isAdmin", Objects.equals(userId, 1));
        return "redirect:/student/login/success";
    }

    @PostMapping
    public String doLogin(HttpServletRequest request, @Valid @ModelAttribute LoginRequest loginRequest,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        boolean isSuccess = loginService.check(loginRequest.getId(), loginRequest.getPw());
        if (!isSuccess) {
            return "thymeleaf/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute("userId", loginRequest.getId());
        return "redirect:/student/login";
    }

    @GetMapping("/success")
    public ModelAndView loginSuccess(HttpSession session) {
        ModelAndView mav = new ModelAndView("thymeleaf/loginSuccess");
        mav.addObject("userId", session.getAttribute("userId"));
        return mav;
    }
}
