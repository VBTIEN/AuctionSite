package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.StatusRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.StatusResponse;
import com.example.AuctionSite.entity.Status;
import com.example.AuctionSite.service.StatusService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StatusController {
    StatusService statusService;
    
    @PostMapping("/create_status")
    ApiResponse<StatusResponse> cerateStatus(@RequestBody StatusRequest statusRequest) {
        return ApiResponse.<StatusResponse>builder()
            .result(statusService.createStatus(statusRequest))
            .build();
    }
    
    @GetMapping("/get_all_status")
    ApiResponse<List<StatusResponse>> getAllStatus() {
        return ApiResponse.<List<StatusResponse>>builder()
            .result(statusService.getAllStatus())
            .build();
    }
    
    @GetMapping("/get_status_by_name/{name}")
    ApiResponse<StatusResponse> getStatusByName(@PathVariable("name") String name) {
        return ApiResponse.<StatusResponse>builder()
            .result(statusService.getStatusByName(name))
            .build();
    }
    
    @PutMapping("/update_status/{name}")
    ApiResponse<StatusResponse> updateStatus(@PathVariable("name") String name, @RequestBody StatusRequest statusRequest) {
        return ApiResponse.<StatusResponse>builder()
            .result(statusService.updateStatus(name, statusRequest))
            .build();
    }
    
    @DeleteMapping("/delete_status/{name}")
    ApiResponse<String> deleteStatus(@PathVariable("name") String name) {
        statusService.deleteStatus(name);
        return ApiResponse.<String>builder()
            .result("Status deleted")
            .build();
    }
}
