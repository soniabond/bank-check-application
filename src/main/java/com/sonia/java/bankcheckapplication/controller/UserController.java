package com.sonia.java.bankcheckapplication.controller;


import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.bank.merchant.PrivatBankMerchantEntity;
import com.sonia.java.bankcheckapplication.model.bank.resp.CategoryDischargeResponse;
import com.sonia.java.bankcheckapplication.model.user.req.ChangeUserPasswordRequest;
import com.sonia.java.bankcheckapplication.model.user.req.MonoBankMerchantRequest;
import com.sonia.java.bankcheckapplication.model.user.req.PrivatBankMerchantRequest;
import com.sonia.java.bankcheckapplication.model.user.req.SaveUserRequest;
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
import java.io.IOException;
import java.util.List;

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

    @PostMapping("/me/merchants/privat")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPrivatMerchant(@AuthenticationPrincipal String email,
                                  @RequestBody PrivatBankMerchantRequest request){
        System.out.println(request);
        userService.addPrivatBankMerchant(email, request);
    }

    @PostMapping("/me/merchants/mono")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMonoMerchant(@AuthenticationPrincipal String email,
                                  @RequestBody MonoBankMerchantRequest request){
        System.out.println(request);
        userService.addMonoBankMerchant(email, request);
    }

    @GetMapping("/me/categories")
    public List<CategoryDischargeResponse> getCategoryAnalytics(@AuthenticationPrincipal String email) throws IOException {
        return userService.generateCategorySplitAnswer(email, 3, 2021);

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
