package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.IntrospectRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.request.AuthenticationRequest;
import com.example.AuctionSite.dto.response.AuthenticationResponse;
import com.example.AuctionSite.dto.response.IntrospectResponse;
import com.example.AuctionSite.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/authenticates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    
    @PostMapping("/get_token")
    ApiResponse<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        var authenticateResult = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
            .result(authenticateResult)
            .build();
    }
    
    @PostMapping("/introspect_token")
    ApiResponse<IntrospectResponse> introspectUser(@RequestBody IntrospectRequest introspectRequest) throws ParseException, JOSEException {
        var introspectResult = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
            .result(introspectResult)
            .build();
    }
}
