package com.tistory.jaimemin.redisdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    private static final String KEY = "name";

    @GetMapping("/login")
    public String login(HttpSession session, @RequestParam String name) {
        session.setAttribute(KEY, name);

        return "login success.";
    }

    @GetMapping("/my-name")
    public String myName(HttpSession session) {
        String myName = (String) session.getAttribute(KEY);

        return myName;
    }
}
