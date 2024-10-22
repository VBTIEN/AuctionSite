package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.RoleRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.RoleResponse;
import com.example.AuctionSite.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;
    
    @PostMapping("/create_role")
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
            .result(roleService.createRole(roleRequest))
            .build();
    }
    
    @GetMapping("/get_all_roles")
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
            .result(roleService.getAllRoles())
            .build();
    }
    
    @DeleteMapping("/delete_role/{roleName}")
    ApiResponse<String> deleteRole(@PathVariable("roleName") String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.<String>builder()
            .result("Role deleted")
            .build();
    }
}
