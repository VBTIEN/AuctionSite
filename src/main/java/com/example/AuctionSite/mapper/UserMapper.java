package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.UserCreateRequest;
import com.example.AuctionSite.dto.request.UserUpdateRequest;
import com.example.AuctionSite.dto.response.UserResponse;
import com.example.AuctionSite.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "ranks", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "joiningDate", ignore = true)
    @Mapping(target = "actionTime", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUser(UserCreateRequest userCreateRequest);
    
    UserResponse toUserResponse(User user);
    
    List<UserResponse> toListUserResponse(List<User> users);
    
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "ranks", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "joiningDate", ignore = true)
    @Mapping(target = "actionTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
