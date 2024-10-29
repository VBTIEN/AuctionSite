package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.CostRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.CostResponse;
import com.example.AuctionSite.service.CostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/costs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CostController {
    CostService costService;
    
    @PostMapping("/create_cost")
    ApiResponse<CostResponse> createCost(@RequestBody CostRequest costRequest) {
        return ApiResponse.<CostResponse>builder()
            .result(costService.createCost(costRequest))
            .build();
    }
    
    @GetMapping("/get_all_costs")
    ApiResponse<List<CostResponse>> getAllCosts() {
        return ApiResponse.<List<CostResponse>>builder()
            .result(costService.getAllCosts())
            .build();
    }
    
    @GetMapping("/get_cost_by_name/{costName}")
    ApiResponse<CostResponse> getCostByName(@PathVariable("costName") String costName) {
        return ApiResponse.<CostResponse>builder()
            .result(costService.getCostByName(costName))
            .build();
    }
    
    @PutMapping("/update_cost/{costName}")
    ApiResponse<CostResponse> updateCost(@PathVariable("costName") String costName, @RequestBody CostRequest costRequest) {
        return ApiResponse.<CostResponse>builder()
            .result(costService.updateCost(costName, costRequest))
            .build();
    }
    
    @DeleteMapping("/delete_cost/{costName}")
    ApiResponse<String> deleteCost(@PathVariable("costName") String costName) {
        costService.deleteCost(costName);
        return ApiResponse.<String>builder()
            .result("Cost deleted")
            .build();
    }
}
