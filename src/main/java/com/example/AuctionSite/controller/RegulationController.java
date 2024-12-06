package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.RegulationRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.RegulationResponse;
import com.example.AuctionSite.service.RegulationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/regulations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RegulationController {
    RegulationService regulationService;

    @PostMapping("/create_regulation")
    ApiResponse<RegulationResponse> createRegulation(@Valid @RequestBody RegulationRequest regulationRequest) {
        return ApiResponse.<RegulationResponse>builder()
                .result(regulationService.createRegulation(regulationRequest))
                .build();
    }

    @GetMapping("/get_all_regulations")
    ApiResponse<List<RegulationResponse>> getAllRegulations() {
        return ApiResponse.<List<RegulationResponse>>builder()
                .result(regulationService.getAllRegulations())
                .build();
    }

    @GetMapping("/get_regulation_by_name/{name}")
    ApiResponse<RegulationResponse> getRegulationByName(@PathVariable("name") String name) {
        return ApiResponse.<RegulationResponse>builder()
                .result(regulationService.getRegulationByName(name))
                .build();
    }

    @PutMapping("/update_regulation/{name}")
    ApiResponse<RegulationResponse> updateRegulation(
            @PathVariable("name") String name, @RequestBody RegulationRequest regulationRequest) {
        return ApiResponse.<RegulationResponse>builder()
                .result(regulationService.updateRegulation(name, regulationRequest))
                .build();
    }

    @DeleteMapping("/delete_regulation/{name}")
    ApiResponse<String> deleteRegulation(@PathVariable("name") String name) {
        regulationService.deleteRegulation(name);
        return ApiResponse.<String>builder().result("Regulation deleted").build();
    }
}
