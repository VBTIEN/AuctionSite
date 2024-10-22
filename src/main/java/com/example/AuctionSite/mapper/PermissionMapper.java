package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.PermissionRequest;
import com.example.AuctionSite.dto.response.PermissionResponse;
import com.example.AuctionSite.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    
    PermissionResponse toPermissionResponse(Permission permission);
}
