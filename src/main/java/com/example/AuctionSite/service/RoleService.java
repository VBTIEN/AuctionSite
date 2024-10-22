package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.RoleRequest;
import com.example.AuctionSite.dto.response.RoleResponse;
import com.example.AuctionSite.entity.Role;
import com.example.AuctionSite.mapper.RoleMapper;
import com.example.AuctionSite.repository.PermissionRepository;
import com.example.AuctionSite.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    
    public RoleResponse createRole(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
    
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
            .stream().map(roleMapper::toRoleResponse).toList();
    }
    
    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }
}
