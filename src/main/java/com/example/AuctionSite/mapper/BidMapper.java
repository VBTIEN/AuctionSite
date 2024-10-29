package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.response.BidResponse;
import com.example.AuctionSite.entity.Bid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidMapper {
    BidResponse toBidResponse(Bid bid);
}
