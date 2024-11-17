package com.example.AuctionSite.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.UserCreateRequest;
import com.example.AuctionSite.dto.request.UserUpdateRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.UserResponse;
import com.example.AuctionSite.entity.User;
import com.example.AuctionSite.exception.AppException;
import com.example.AuctionSite.exception.ErrorCode;
import com.example.AuctionSite.repository.UserRepository;
import com.example.AuctionSite.service.AuthenticationService;
import com.example.AuctionSite.service.UserService;
import com.nimbusds.jose.JOSEException;

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
    UserRepository userRepository;
    AuthenticationService authenticationService;

    @PostMapping("/register")
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
            @PathVariable("id") String id,
            @RequestBody UserUpdateRequest userUpdateRequest,
            @RequestHeader("Authorization") String token)
            throws ParseException, JOSEException {
        String username = authenticationService.extractUsername(token.replace("Bearer ", ""));

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!user.getUsername().equals(username) && !"admin".equals(username)) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }

        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userUpdateRequest, id))
                .build();
    }

    @DeleteMapping("/delete_user/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") String id, @RequestHeader("Authorization") String token)
            throws ParseException, JOSEException {
        String username = authenticationService.extractUsername(token.replace("Bearer ", ""));

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!user.getUsername().equals(username) && !"admin".equals(username)) {
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        }

        userService.deleteUser(id);
        return ApiResponse.<String>builder().result("User deleted").build();
    }

    @GetMapping("/get_all_users_joined_by_auctionid_of_user")
    ApiResponse<Set<UserResponse>> getAuctionParticipantsOfUser(@RequestParam Integer auctionId) {
        return ApiResponse.<Set<UserResponse>>builder()
                .result(userService.getAuctionParticipantsOfUserCreate(auctionId))
                .build();
    }

    @GetMapping("/get_all_users_joined_by_auctionid")
    ApiResponse<Set<UserResponse>> getAuctionParticipants(@RequestParam Integer auctionId) {
        return ApiResponse.<Set<UserResponse>>builder()
                .result(userService.getAuctionParticipants(auctionId))
                .build();
    }
}
