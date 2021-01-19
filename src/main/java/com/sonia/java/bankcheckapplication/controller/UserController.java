package com.sonia.java.bankcheckapplication.controller;


import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.user.ChangeUserPasswordRequest;
import com.sonia.java.bankcheckapplication.model.user.SaveUserRequest;
import com.sonia.java.bankcheckapplication.model.user.UserResponse;
import com.sonia.java.bankcheckapplication.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal String email) {
        return userService.findByEmail(email).orElseThrow(() -> CardCheckExceptions.userNotFound(email));
    }

    @GetMapping
    @PageableAsQueryParam
    public Page<UserResponse> listUsers(@Parameter(hidden = true) Pageable pageable) {
        return userService.list(pageable);
    }

    @PatchMapping("/me/password")
    public UserResponse changeCurrentUserPassword(@AuthenticationPrincipal String email,
                                                  @RequestBody @Valid ChangeUserPasswordRequest request) {
        return userService.changePasswordByEmail(email, request);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid SaveUserRequest request){
       return userService.create(request);
    }
}
