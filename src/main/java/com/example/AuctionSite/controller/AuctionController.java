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

    @GetMapping("/get_all_auctions_by_userid/{userid}")
    ApiResponse<List<AuctionResponse>> getAllAuctionsByUserId(@PathVariable("userid") String userid) {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAllAuctionsByUserId(userid))
                .build();
    }

    @GetMapping("/get_all_auctions_of_user")
    ApiResponse<List<AuctionResponse>> getAllAuctionsOfUser() {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAllAuctionsOfUser())
                .build();
    }

    @GetMapping("/get_auction_by_id_of_user/{id}")
    ApiResponse<AuctionResponse> getAuctionByIdOfUser(@PathVariable("id") Integer id) {
        return ApiResponse.<AuctionResponse>builder()
                .result(auctionService.getAuctionByIdOfUser(id))
                .build();
    }

    @GetMapping("/get_auction_by_name_of_user/{name}")
    ApiResponse<List<AuctionResponse>> getAuctionByNameOfUser(@PathVariable("name") String name) {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAuctionByNameOfUser(name))
                .build();
    }

    @GetMapping("/auctions_pending_paged_of_user")
    ApiResponse<AuctionPageResponse> getAllAuctionsPendingPagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<AuctionPageResponse>builder()
                .result(auctionService.getAllAuctionsPendingPagedOfUser(page, size))
                .build();
    }

    @GetMapping("/auctions_ongoing_paged_of_user")
    ApiResponse<AuctionPageResponse> getAllAuctionsOngoingPagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<AuctionPageResponse>builder()
                .result(auctionService.getAllAuctionsOngoingPagedOfUser(page, size))
                .build();
    }

    @GetMapping("/auctions_ended_paged_of_user")
    ApiResponse<AuctionPageResponse> getAllAuctionsEndedPagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<AuctionPageResponse>builder()
                .result(auctionService.getAllAuctionsEndedPagedOfUser(page, size))
                .build();
    }

    @GetMapping("/get_all_auctions_of_user_joined")
    ApiResponse<List<AuctionResponse>> getAllAuctionsOfUserJoined() {
        return ApiResponse.<List<AuctionResponse>>builder()
                .result(auctionService.getAllAuctionsOfUserJoined())
                .build();
    }
}
