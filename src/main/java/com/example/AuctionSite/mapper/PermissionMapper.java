package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.PermissionRequest;
import com.example.AuctionSite.dto.response.PermissionResponse;
import com.example.AuctionSite.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);

    PermissionResponse toPermissionResponse(Permission permission);

    void toUpdatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);
}
