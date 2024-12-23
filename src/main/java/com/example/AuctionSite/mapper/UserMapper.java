package com.example.AuctionSite.mapper;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.UserCreateRequest;
import com.example.AuctionSite.dto.request.UserUpdateRequest;
import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.dto.response.UserResponse;
import com.example.AuctionSite.entity.Follow;
import com.example.AuctionSite.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "salesReceipt", ignore = true)
    @Mapping(target = "purchaseReceipt", ignore = true)
    @Mapping(target = "joinedAuctions", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "follows", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "ranks", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "notifications", ignore = true)
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

    @Mapping(target = "follows", expression = "java(mapFollowsToFollowResponses(user.getFollows()))")
    @Mapping(target = "actionTime", source = "actionTime", qualifiedByName = "durationToString")
    UserResponse toUserResponse(User user);

    default Set<FollowResponse> mapFollowsToFollowResponses(Set<Follow> follows) {
        if (follows == null) {
            return Collections.emptySet();
        }
        return follows.stream()
                .map(follow -> FollowResponse.builder()
                        .followed(follow.getAuction().getId())
                        .build())
                .collect(Collectors.toSet());
    }

    @Mapping(target = "actionTime", source = "actionTime", qualifiedByName = "durationToString")
    List<UserResponse> toListUserResponse(List<User> users);

    @Mapping(target = "salesReceipt", ignore = true)
    @Mapping(target = "purchaseReceipt", ignore = true)
    @Mapping(target = "joinedAuctions", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "follows", ignore = true)
    @Mapping(target = "rate", ignore = true)
    @Mapping(target = "ranks", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "notifications", ignore = true)
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

    @org.mapstruct.Named("durationToString")
    default String durationToString(Duration duration) {
        if (duration == null) {
            return "00:00";
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }
}
