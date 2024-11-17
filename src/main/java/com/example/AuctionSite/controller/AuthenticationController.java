package com.example.AuctionSite.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.AuthenticationRequest;
import com.example.AuctionSite.dto.request.IntrospectRequest;
import com.example.AuctionSite.dto.request.LogoutRequest;
import com.example.AuctionSite.dto.request.RefreshRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.AuthenticationResponse;
import com.example.AuctionSite.dto.response.IntrospectResponse;
import com.example.AuctionSite.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/authenticates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        var authenticateResult = authenticationService.login(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticateResult)
                .build();
    }

    @PostMapping("/introspect_token")
    ApiResponse<IntrospectResponse> introspectUser(@RequestBody IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {
        var introspectResult = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(introspectResult)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<String> logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return ApiResponse.<String>builder().result("User logouted").build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest refreshRequest)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(refreshRequest);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}
