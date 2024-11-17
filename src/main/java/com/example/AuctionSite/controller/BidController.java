package com.example.AuctionSite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.BidRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.BidResponse;
import com.example.AuctionSite.service.BidService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BidController {
    BidService bidService;

    @PostMapping("/create_bid")
    ApiResponse<BidResponse> createBid(@RequestParam Integer auctionid, @RequestBody BidRequest bidRequest) {
        return ApiResponse.<BidResponse>builder()
                .result(bidService.createBid(auctionid, bidRequest))
                .build();
    }

    @GetMapping("/get_all_bids_by_auctionid")
    ApiResponse<List<BidResponse>> getAllBidsByAuctionId(@RequestParam Integer auctionid) {
        return ApiResponse.<List<BidResponse>>builder()
                .result(bidService.getAllBidsByAuctionId(auctionid))
                .build();
    }

    @GetMapping("/get_all_bids_by_userid")
    ApiResponse<List<BidResponse>> getAllBidsByUserId(@RequestParam String userid) {
        return ApiResponse.<List<BidResponse>>builder()
                .result(bidService.getAllBidsByUserId(userid))
                .build();
    }

    @GetMapping("/get_all_bids_of_user")
    ApiResponse<List<BidResponse>> getAllBidsOfUser() {
        return ApiResponse.<List<BidResponse>>builder()
                .result(bidService.getAllBidsOfUser())
                .build();
    }

    @GetMapping("/get_all_bids_by_auctionid_of_user")
    ApiResponse<List<BidResponse>> getAllBidsByAuctionIdOfUser(@RequestParam Integer auctionid) {
        return ApiResponse.<List<BidResponse>>builder()
                .result(bidService.getAllBidsByAuctionIdOfUser(auctionid))
                .build();
    }
}
