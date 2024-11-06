package com.example.AuctionSite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.RateRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.RateResponse;
import com.example.AuctionSite.service.RateService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RateController {
    RateService rateService;

    @PostMapping("/create_rate")
    ApiResponse<RateResponse> createRate(@RequestBody RateRequest rateRequest) {
        return ApiResponse.<RateResponse>builder()
                .result(rateService.createRate(rateRequest))
                .build();
    }

    @GetMapping("/get_all_rates")
    ApiResponse<List<RateResponse>> getAllRates() {
        return ApiResponse.<List<RateResponse>>builder()
                .result(rateService.getAllRates())
                .build();
    }

    @GetMapping("/get_rate_by_numberOfStar/{numberOfStar}")
    ApiResponse<RateResponse> getRateByNumberOfStar(@PathVariable("numberOfStar") Float numberOfStar) {
        return ApiResponse.<RateResponse>builder()
                .result(rateService.getRateByNumberOfStar(numberOfStar))
                .build();
    }

    @PutMapping("/update_rate/{numberOfStar}")
    ApiResponse<RateResponse> updateRate(
            @PathVariable("numberOfStar") Float numberOfStarFloat, @RequestBody RateRequest rateRequest) {
        return ApiResponse.<RateResponse>builder()
                .result(rateService.updateRate(numberOfStarFloat, rateRequest))
                .build();
    }

    @DeleteMapping("/delete_rate/{numberOfStar}")
    ApiResponse<String> deleteRate(@PathVariable("numberOfStar") Float numberOfStar) {
        rateService.deleteRate(numberOfStar);
        return ApiResponse.<String>builder().result("Rate deleted").build();
    }
}
