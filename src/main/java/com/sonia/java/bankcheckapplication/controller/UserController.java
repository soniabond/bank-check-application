package com.sonia.java.bankcheckapplication.controller;


import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.user.SaveUserRequest;
import com.sonia.java.bankcheckapplication.model.user.UserResponse;
import com.sonia.java.bankcheckapplication.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal String email) {
        return userService.findByEmail(email).orElseThrow(() -> CardCheckExceptions.userNotFound(email));
    }


    @PostMapping
    public UserResponse register(@RequestBody @Valid SaveUserRequest request){
       return userService.create(request);
    }
}
