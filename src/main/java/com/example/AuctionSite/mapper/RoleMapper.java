package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.RoleRequest;
import com.example.AuctionSite.dto.response.RoleResponse;
import com.example.AuctionSite.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);
    
    RoleResponse toRoleResponse(Role role);
    
    @Mapping(target = "permissions", ignore = true)
    void toUpdateRole(@MappingTarget Role role, RoleRequest roleRequest);
}
