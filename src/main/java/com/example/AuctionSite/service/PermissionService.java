package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.PermissionRequest;
import com.example.AuctionSite.dto.response.PermissionResponse;
import com.example.AuctionSite.entity.Permission;
import com.example.AuctionSite.mapper.PermissionMapper;
import com.example.AuctionSite.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    
    public PermissionResponse createPermission(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    
    public List<PermissionResponse> getAllPermissions() {
        var permission = permissionRepository.findAll();
        return permission.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    
    public void deletePermission(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
