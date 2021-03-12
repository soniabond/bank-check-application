package com.sonia.java.bankcheckapplication.controller;


import com.sonia.java.bankcheckapplication.exceptions.CardCheckExceptions;
import com.sonia.java.bankcheckapplication.model.bank.req.CategoryDischargeRequest;
import com.sonia.java.bankcheckapplication.model.bank.resp.BalanceResponse;
import com.sonia.java.bankcheckapplication.model.bank.resp.CategoryDischargeResponse;
import com.sonia.java.bankcheckapplication.model.bank.resp.UserCategoryLimitResponse;
import com.sonia.java.bankcheckapplication.model.user.req.*;
import com.sonia.java.bankcheckapplication.model.user.UserResponse;
import com.sonia.java.bankcheckapplication.service.CategoryService;
import com.sonia.java.bankcheckapplication.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final CategoryService categoryService;

    public UserController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
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

    @PostMapping("/categories/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategories(@AuthenticationPrincipal String email,
                              @RequestBody List<CategoryDischargeRequest> categoryDischargeRequests){
        System.out.println(categoryDischargeRequests);
        categoryService.addCategoryDischarges(categoryDischargeRequests);
    }

    @PostMapping("me/categories/limits/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategories(@AuthenticationPrincipal String email, @RequestBody ChangeCategoryLimitRequest request){
        System.out.println("LIMIT");
        userService.setCategoryLimit(email, request.getCategoryName(), request.getNewLimit());
    }

    @GetMapping("me/categories/limits")
    public List<UserCategoryLimitResponse> getCategories(@AuthenticationPrincipal String email){
        return userService.getAllCategoriesWithLimits(email);
    }

    @GetMapping("me/categories/limit")
    public UserCategoryLimitResponse getCategories(@AuthenticationPrincipal String email,  CategoryLimitRequest request){
        System.out.println("HELLLO");
        UserCategoryLimitResponse response = userService.getCategoryLimit(email, request.getCategoryName());
        System.out.println(response.getCategoryName() + ' ' + response.getLimit());
        return response;
    }

    @GetMapping("/me/discharges")
    public Set<CategoryDischargeResponse> getCategoryAnalytics(@AuthenticationPrincipal String email, @RequestParam("month") int month,
                                                               @RequestParam("year") int year) throws IOException {
        return userService.generateCategorySplitAnswer(email, month, year);

    }

    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAdmin(@RequestBody @Valid SaveUserRequest request) {
        return userService.createAdmin(request);
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

    @GetMapping("/me/balance")
    public BalanceResponse getBalance(@AuthenticationPrincipal String email){

        float balance = this.userService.getMerchantTotalBalance(email)*100/100;
        return new
                BalanceResponse(balance);

    }


}
