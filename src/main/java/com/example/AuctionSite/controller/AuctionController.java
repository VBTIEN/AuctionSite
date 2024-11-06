package com.example.AuctionSite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.AuctionRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.AuctionPageResponse;
import com.example.AuctionSite.dto.response.AuctionResponse;
import com.example.AuctionSite.service.AuctionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auctions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuctionController {
    AuctionService auctionService;

    @PostMapping("/create_auction")
    ApiResponse<AuctionResponse> createAuction(@RequestBody AuctionRequest auctionRequest) {
        return ApiResponse.<AuctionResponse>builder()
                .result(auctionService.createAuction(auctionRequest))
                .build();
    }

    @GetMapping("/get_all_auctions")
    ApiResponse<List<AuctionResponse>> getAllAuctions() {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAllProducts())
                .build();
    }

    @GetMapping("/get_auction_by_id/{auctionid}")
    ApiResponse<AuctionResponse> getAuctionById(@PathVariable("auctionid") Integer auctionid) {
        return ApiResponse.<AuctionResponse>builder()
                .result(auctionService.getAuctionById(auctionid))
                .build();
    }

    @GetMapping("/get_auction_by_name/{auctionName}")
    ApiResponse<List<AuctionResponse>> getAuctionByName(@PathVariable("auctionName") String auctionName) {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAuctionByName(auctionName))
                .build();
    }

    @PutMapping("/update_auction/{auctionid}")
    ApiResponse<AuctionResponse> updateAuction(
            @PathVariable("auctionid") Integer auctionid, @RequestBody AuctionRequest auctionRequest) {
        return ApiResponse.<AuctionResponse>builder()
                .result(auctionService.updateAuction(auctionid, auctionRequest))
                .build();
    }

    @DeleteMapping("/delete_auction/{auctionid}")
    ApiResponse<String> deleteAuction(@PathVariable("auctionid") Integer auctionid) {
        auctionService.deleteAuction(auctionid);
        return ApiResponse.<String>builder().result("Auction deleted").build();
    }

    @GetMapping("/search_auction_by_name")
    ApiResponse<List<AuctionResponse>> searchAuctionsByName(
            @RequestParam String name, @RequestParam(defaultValue = "30") int threshold) {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.searchAuctionsByName(name, threshold))
                .build();
    }

    @GetMapping("/get_auctions_pending")
    ApiResponse<List<AuctionResponse>> getAuctionsPending() {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAuctionsPending())
                .build();
    }

    @GetMapping("/get_auctions_ongoing")
    ApiResponse<List<AuctionResponse>> getAuctionsOngoing() {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAuctionsOngoing())
                .build();
    }

    @GetMapping("/get_auctions_ended")
    ApiResponse<List<AuctionResponse>> getAuctionsEnded() {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAuctionsEnded())
                .build();
    }

    @GetMapping("/auctions_paged")
    ApiResponse<AuctionPageResponse> getAllAuctionsPaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<AuctionPageResponse>builder()
                .result(auctionService.getAllAuctionsPaged(page, size))
                .build();
    }

    @GetMapping("/auctions_pending_paged")
    ApiResponse<AuctionPageResponse> getAllAuctionsPendingPaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<AuctionPageResponse>builder()
                .result(auctionService.getAllAuctionsPendingPaged(page, size))
                .build();
    }

    @GetMapping("/auctions_ongoing_paged")
    ApiResponse<AuctionPageResponse> getAllAuctionsOngoingPaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<AuctionPageResponse>builder()
                .result(auctionService.getAllAuctionsOngoingPaged(page, size))
                .build();
    }

    @GetMapping("/auctions_ended_paged")
    ApiResponse<AuctionPageResponse> getAllAuctionsEndedPaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<AuctionPageResponse>builder()
                .result(auctionService.getAllAuctionsEndedPaged(page, size))
                .build();
    }
}
