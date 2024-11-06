package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;

import com.example.AuctionSite.dto.response.BidResponse;
import com.example.AuctionSite.entity.Bid;

@Mapper(componentModel = "spring")
public interface BidMapper {
    BidResponse toBidResponse(Bid bid);
}
