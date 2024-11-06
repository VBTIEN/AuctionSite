package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.UserCreateRequest;
import com.example.AuctionSite.dto.request.UserUpdateRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.UserResponse;
import com.example.AuctionSite.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping("/create_user")
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userCreateRequest))
                .build();
    }

    @GetMapping("/get_all_users")
    ApiResponse<List<UserResponse>> getAllUsers() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", auth.getName());
        auth.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/get_user_by_id/{id}")
    ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .build();
    }

    @GetMapping("/get_user_by_username/{username}")
    ApiResponse<UserResponse> getUserByUsername(@PathVariable("username") String userName) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserByUsername(userName))
                .build();
    }

    @GetMapping("/get_my_info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/update_user/{id}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable("id") String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userUpdateRequest, id))
                .build();
    }

    @DeleteMapping("/delete_user/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder().result("User deleted").build();
    }
}
