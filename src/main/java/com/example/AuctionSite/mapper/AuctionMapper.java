package com.example.AuctionSite.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.AuctionRequest;
import com.example.AuctionSite.dto.response.AuctionResponse;
import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Follow;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "numberOfBids", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "follows", ignore = true)
    @Mapping(target = "finalCost", ignore = true)
    @Mapping(target = "bids", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(target = "cost", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "step", ignore = true)
    Auction toAuction(AuctionRequest auctionRequest);

    @Mapping(target = "follows", expression = "java(mapFollowsToFollowResponses(auction.getFollows()))")
    AuctionResponse toAuctionResponse(Auction auction);

    default Set<FollowResponse> mapFollowsToFollowResponses(Set<Follow> follows) {
        if (follows == null) {
            return Collections.emptySet();
        }
        return follows.stream()
                .map(follow -> FollowResponse.builder()
                        .followed_by(follow.getUser().getUsername())
                        .build())
                .collect(Collectors.toSet());
    }

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "numberOfBids", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "follows", ignore = true)
    @Mapping(target = "finalCost", ignore = true)
    @Mapping(target = "bids", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(target = "cost", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "step", ignore = true)
    void toUpdateAuction(@MappingTarget Auction auction, AuctionRequest auctionRequest);
}
