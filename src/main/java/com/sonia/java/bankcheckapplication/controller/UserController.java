package com.sonia.java.bankcheckapplication.controller;


import com.sonia.java.bankcheckapplication.model.user.SaveRegularUserRequest;
import com.sonia.java.bankcheckapplication.model.user.UserResponse;
import com.sonia.java.bankcheckapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse register(@RequestBody @Valid SaveRegularUserRequest request){
       return userService.create(request);
    }
}
