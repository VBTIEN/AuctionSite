package com.example.AuctionSite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.service.FollowService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FollowController {
    FollowService followService;

    @PostMapping("/add_follow_auction")
    ApiResponse<FollowResponse> addFollowAuction(@RequestParam Integer auctionid) {
        return ApiResponse.<FollowResponse>builder()
                .result(followService.addFollowAuction(auctionid))
                .build();
    }

    @DeleteMapping("/unfollow_auction")
    ApiResponse<String> unfollowAuction(@RequestParam Integer auctionid) {
        followService.unfollowAuction(auctionid);
        return ApiResponse.<String>builder().result("Follow deleted").build();
    }

    @GetMapping("/get_all_follows_by_userid/{userid}")
    ApiResponse<List<FollowResponse>> getAllFollowByUserid(@PathVariable("userid") String userid) {
        return ApiResponse.<List<FollowResponse>>builder()
                .result(followService.getAllFollowByUserid(userid))
                .build();
    }

    @GetMapping("/get_all_follows_by_auctionid/{auctionid}")
    ApiResponse<List<FollowResponse>> getAllFollowByAuctionid(@PathVariable("auctionid") Integer auctionid) {
        return ApiResponse.<List<FollowResponse>>builder()
                .result(followService.getAllFollowByAuctionid(auctionid))
                .build();
    }

    @GetMapping("/get_all_follows_of_user")
    ApiResponse<List<FollowResponse>> getAllFollowsOfUser() {
        return ApiResponse.<List<FollowResponse>>builder()
                .result(followService.getAllFollowsOfUser())
                .build();
    }
}
