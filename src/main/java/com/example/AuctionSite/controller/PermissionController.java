package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.PermissionRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.PermissionResponse;
import com.example.AuctionSite.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;
    
    @PostMapping("/create_permission")
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
            .result(permissionService.createPermission(permissionRequest))
            .build();
    }
    
    @GetMapping("/get_all_permissions")
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
            .result(permissionService.getAllPermissions())
            .build();
    }
    
    @PutMapping("/update_permission/{permissionName}")
    ApiResponse<PermissionResponse> updatePermission(@PathVariable("permissionName") String permissionName, @RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
            .result(permissionService.updatePermission(permissionName, permissionRequest))
            .build();
    }
    
    @DeleteMapping("/delete_permission/{permissionName}")
    ApiResponse<String> deletePermission(@PathVariable("permissionName") String permissionName) {
        permissionService.deletePermission(permissionName);
        return ApiResponse.<String>builder()
            .result("Permission deleted")
            .build();
    }
}
