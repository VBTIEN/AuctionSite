package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.BenefitRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.BenefitResponse;
import com.example.AuctionSite.service.BenefitService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/benefits")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BenefitController {
    BenefitService benefitService;

    @PostMapping("/create_benefit")
    ApiResponse<BenefitResponse> createBenefit(@Valid @RequestBody BenefitRequest benefitRequest) {
        return ApiResponse.<BenefitResponse>builder()
                .result(benefitService.createBenefit(benefitRequest))
                .build();
    }

    @GetMapping("/get_all_benefits")
    ApiResponse<List<BenefitResponse>> getAllBenefits() {
        return ApiResponse.<List<BenefitResponse>>builder()
                .result(benefitService.getAllBenefits())
                .build();
    }

    @GetMapping("/get_benefit_by_name/{benefitName}")
    ApiResponse<BenefitResponse> getBenefitByName(@PathVariable("benefitName") String benefitName) {
        return ApiResponse.<BenefitResponse>builder()
                .result(benefitService.getBenefitByName(benefitName))
                .build();
    }

    @PutMapping("/update_benefit/{benefitName}")
    ApiResponse<BenefitResponse> udpateBenefit(
            @PathVariable("benefitName") String benefitName, @RequestBody BenefitRequest benefitRequest) {
        return ApiResponse.<BenefitResponse>builder()
                .result(benefitService.updateBenefit(benefitName, benefitRequest))
                .build();
    }

    @DeleteMapping("/delete_benefit/{benefitName}")
    ApiResponse<String> deleteBenefit(@PathVariable("benefitName") String benefitName) {
        benefitService.deleteBenefit(benefitName);
        return ApiResponse.<String>builder().result("Benefit deleted").build();
    }
}
