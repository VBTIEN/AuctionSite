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
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public RoleResponse createRole(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_ROLES')")
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public RoleResponse updateRole(String name, RoleRequest roleRequest) {
        Role role = roleRepository.findById(name).orElseThrow(() -> new RuntimeException("Role not found"));
        roleMapper.toUpdateRole(role, roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }
    
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }
}
