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
    
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "ranks", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "follows", ignore = true)
    @Mapping(target = "bids", ignore = true)
    @Mapping(target = "auctions", ignore = true)
    @Mapping(target = "sumOfRate", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "numberOfRate", ignore = true)
    @Mapping(target = "joiningDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actionTime", ignore = true)
    User toUser(UserCreateRequest userCreateRequest);
    
    UserResponse toUserResponse(User user);
    
    List<UserResponse> toListUserResponse(List<User> users);
    
    
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "ranks", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "follows", ignore = true)
    @Mapping(target = "bids", ignore = true)
    @Mapping(target = "auctions", ignore = true)
    @Mapping(target = "sumOfRate", ignore = true)
    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "numberOfRate", ignore = true)
    @Mapping(target = "joiningDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "actionTime", ignore = true)
    void toUpdateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);
}
