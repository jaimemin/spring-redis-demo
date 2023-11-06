package com.tistory.jaimemin.redisdemo.controller;

import com.tistory.jaimemin.redisdemo.dto.UserProfile;
import com.tistory.jaimemin.redisdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;

    @GetMapping("/users/{userId}/profile")
    public UserProfile getUserProfile(@PathVariable String userId) throws InterruptedException {
        return userService.getUserProfile(userId);
    }
}
