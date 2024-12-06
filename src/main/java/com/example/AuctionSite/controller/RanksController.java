package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.RanksRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.RanksResponse;
import com.example.AuctionSite.service.RanksService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/ranks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RanksController {
    RanksService ranksService;

    @PostMapping("/create_rank")
    ApiResponse<RanksResponse> createRank(@Valid @RequestBody RanksRequest ranksRequest) {
        return ApiResponse.<RanksResponse>builder()
                .result(ranksService.createRank(ranksRequest))
                .build();
    }

    @GetMapping("/get_all_ranks")
    ApiResponse<List<RanksResponse>> getAllRanks() {
        return ApiResponse.<List<RanksResponse>>builder()
                .result(ranksService.getAllRanks())
                .build();
    }

    @GetMapping("/get_rank_by_name/{rankName}")
    ApiResponse<RanksResponse> getRankByName(@PathVariable("rankName") String rankName) {
        return ApiResponse.<RanksResponse>builder()
                .result(ranksService.getRankByName(rankName))
                .build();
    }

    @PutMapping("/update_rank/{rankName}")
    ApiResponse<RanksResponse> updateRank(
            @PathVariable("rankName") String rankName, @RequestBody RanksRequest ranksRequest) {
        return ApiResponse.<RanksResponse>builder()
                .result(ranksService.updateRank(rankName, ranksRequest))
                .build();
    }

    @DeleteMapping("/delete_rank/{rankName}")
    ApiResponse<String> deleteRank(@PathVariable("rankName") String rankName) {
        ranksService.deleteRank(rankName);
        return ApiResponse.<String>builder().result("Rank deleted").build();
    }
}
