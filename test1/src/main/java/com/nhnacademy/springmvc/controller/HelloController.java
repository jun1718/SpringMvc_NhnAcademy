package com.nhnacademy.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/test2") // 모든 메서드 방식을 다 받는다. 해당 클래스는 /test2로 다음부터의 것들만 들어옴
public class HelloController {
//    @GetMapping("/aaa")
//    @RequestMapping(method = RequestMethod.GET, value = "/aaa")
//    public String index() {
//        return "index";
//    }
//
//
//    @GetMapping("/test")
//    public String test() {
//        return "test";
//    }
//
////    @RequestMapping(method = RequestMethod.GET, value = "/test2")
//    @RequestMapping(value = "/test2") // 속성값이 하나면 value 생략가능
//    public String test2() {
//        return "test2";
//    }


//    @RequestMapping(value = "/test", method="GET")

    // GET / test GET /test?id= 값은 없어도 되고 파라미터만 잇으면 됨
//    @GetMapping(value = "/test", params = "id")
    @GetMapping(value = "/test", params = "id=aaa")
    public String test1() {
        return null;
    }

//    @GetMapping(value = "/test", params = "!id")
    @GetMapping(value = "/test", params = "id!=aaa")
    public String test2() {
        return null;
    }


//    @GetMapping(value = "/test", params = "id=aaa")
//    public String test(HttpServletResponse response, Model model) {
////        modelMap.put("name", "dong")
//        model.addAttribute("name", "dongmyo");
//        return "index";
//    }


//    @GetMapping("/")
////    public String test(HttpServletResponse response, Model model) {
//        public String test(HttpServletResponse response, ModelMap modelMap, HttpServletRequest request) {
////        model.addAttribute("hello", "dongmyo");
////        modelMap.put("hello", "hello World");
//
//        request.setAttribute("hello", "hello~~~~~");
//        return "index";
//    }

//    @GetMapping("/")
//    public ModelAndView test() {
//        ModelAndView mav = new ModelAndView("index");
//        mav.addObject("hello", "hello, world!");
//        return mav;
//    }



    // /aaaa?name=xxxx
//    @GetMapping("/aaaa")
//    public ModelAndView test(@RequestParam(value = "name", required = false) String name) {
//        ModelAndView mav = new ModelAndView("index");
//        mav.addObject("hello", name);
//        return mav;
//    }


    // GET /users/{userId}?role=admin filterPassword=true
    // User-Agent: Moziila
//    @GetMapping("/users/{userId}")
//    public ModelAndView test(@PathVariable("userId") long userId,
//                             @RequestParam(value = "filterPassword", required=false) String pw,
//                             @RequestHeader("User-Agent") String userAgent) {
//        ModelAndView mav = new ModelAndView("index");
//        mav.addObject("hello", userId + pw + userAgent);
//        return mav;
//    }

    @GetMapping("/users/{userId}")
    public ModelAndView test(@PathVariable("userId") long userId,
                             @RequestParam(value = "filterPassword", required=false) String pw,
                             @RequestHeader("User-Agent") String userAgent,
                             @CookieValue("SESSION") String sessionCookie) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("hello", userId + pw + userAgent);
        return mav;
    }
}
