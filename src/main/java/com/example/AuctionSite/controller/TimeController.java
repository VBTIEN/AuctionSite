package com.example.AuctionSite.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.TimeRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.TimeResponse;
import com.example.AuctionSite.service.TimeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TimeController {
    TimeService timeService;

    @PostMapping("/create_time")
    ApiResponse<TimeResponse> createTime(@RequestBody TimeRequest timeRequest) {
        return ApiResponse.<TimeResponse>builder()
                .result(timeService.createTime(timeRequest))
                .build();
    }

    @GetMapping("/get_all_times")
    ApiResponse<List<TimeResponse>> getAllTimes() {
        return ApiResponse.<List<TimeResponse>>builder()
                .result(timeService.getAllTimes())
                .build();
    }

    @GetMapping("/get_time_by_name/{timeName}")
    ApiResponse<TimeResponse> getTimeByName(@PathVariable("timeName") String timeName) {
        return ApiResponse.<TimeResponse>builder()
                .result(timeService.getTimeByName(timeName))
                .build();
    }

    @PutMapping("/update_time/{timeName}")
    ApiResponse<TimeResponse> updateTime(
            @PathVariable("timeName") String timeName, @RequestBody TimeRequest timeRequest) {
        return ApiResponse.<TimeResponse>builder()
                .result(timeService.updateTime(timeName, timeRequest))
                .build();
    }

    @DeleteMapping("/delete_time/{timeName}")
    ApiResponse<String> deleteTime(@PathVariable("timeName") String timeName) {
        timeService.deleteTime(timeName);
        return ApiResponse.<String>builder().result("Time deleted").build();
    }
}
