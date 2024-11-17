package com.example.AuctionSite.mapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
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
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "participantCount", ignore = true)
    @Mapping(target = "winningBidder", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "remainingTime", ignore = true)
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

    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "participantCount", ignore = true)
    @Mapping(target = "winningBidder", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "remainingTime", ignore = true)
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

    @AfterMapping
    default void calculateRemainingTime(@MappingTarget AuctionResponse auctionResponse, Auction auction) {
        if (auction.getStatus() != null && auction.getStatus().getName().equals("ONGOING")) {
            Duration remaining =
                    auction.getTime().getTime().minus(Duration.between(auction.getStartTime(), LocalDateTime.now()));
            if (!remaining.isNegative()) {
                long hours = remaining.toHours();
                long minutes = remaining.toMinutesPart();
                long seconds = remaining.toSecondsPart();
                auctionResponse.setRemainingTime(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            } else {
                auctionResponse.setRemainingTime("00:00:00");
            }
        } else {
            auctionResponse.setRemainingTime("N/A");
        }
    }
}
