package com.nhnacademy.springmvc.interceptor;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
        if (!requestURI.equals("/") && !requestURI.equals("/student/login")
                && !requestURI.contains("/studentsRest")) {
            if (Objects.isNull(session)) {
                response.sendRedirect("/student/login");
                return false;
            }
            if (Objects.isNull(session.getAttribute("userId"))) {
                response.sendRedirect("/student/login");
                return false;
            }
        }

        return true;
    }
}
