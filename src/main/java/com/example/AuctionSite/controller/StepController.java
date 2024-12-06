package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.StepRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.StepResponse;
import com.example.AuctionSite.service.StepService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/steps")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StepController {
    StepService stepService;

    @PostMapping("/create_step")
    ApiResponse<StepResponse> createStep(@Valid @RequestBody StepRequest stepRequest) {
        return ApiResponse.<StepResponse>builder()
                .result(stepService.createStep(stepRequest))
                .build();
    }

    @GetMapping("/get_all_steps")
    ApiResponse<List<StepResponse>> getAllSteps() {
        return ApiResponse.<List<StepResponse>>builder()
                .result(stepService.getAllSteps())
                .build();
    }

    @GetMapping("/get_step_by_name/{stepName}")
    ApiResponse<StepResponse> getStepByName(@PathVariable("stepName") String stepName) {
        return ApiResponse.<StepResponse>builder()
                .result(stepService.getStepByName(stepName))
                .build();
    }

    @PutMapping("/update_step/{stepName}")
    ApiResponse<StepResponse> updateStep(
            @PathVariable("stepName") String stepName, @RequestBody StepRequest stepRequest) {
        return ApiResponse.<StepResponse>builder()
                .result(stepService.updateStep(stepName, stepRequest))
                .build();
    }

    @DeleteMapping("/delete_step/{stepName}")
    ApiResponse<String> deleteStep(@PathVariable("stepName") String stepName) {
        stepService.deleteStep(stepName);
        return ApiResponse.<String>builder().result("Step deleted").build();
    }
}
