package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.AuctionRequest;
import com.example.AuctionSite.dto.response.AuctionResponse;
import com.example.AuctionSite.entity.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    
    AuctionResponse toAuctionResponse(Auction auction);
    
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
