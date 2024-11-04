package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.FollowRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.service.FollowService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FollowController {
    FollowService followService;
    
    @PostMapping("/add_follow_auction")
    ApiResponse<FollowResponse> addFollowAuction(@RequestBody FollowRequest followRequest) {
        return ApiResponse.<FollowResponse>builder()
            .result(followService.addFollowAuction(followRequest))
            .build();
    }
    
    @DeleteMapping("/unfollow_auction/{id}")
    ApiResponse<String> unfollowAuction(@PathVariable("id") Integer id) {
        followService.unfollowAuction(id);
        return ApiResponse.<String>builder()
            .result("Follow deleted")
            .build();
    }
}
