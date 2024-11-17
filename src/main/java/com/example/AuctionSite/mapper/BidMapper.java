package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.AuctionSite.dto.request.BidRequest;
import com.example.AuctionSite.dto.response.BidResponse;
import com.example.AuctionSite.entity.Bid;

@Mapper(componentModel = "spring")
public interface BidMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auction", ignore = true)
    Bid toBid(BidRequest bidRequest);

    @Mapping(target = "by_user", ignore = true)
    @Mapping(target = "bid_of", ignore = true)
    @Mapping(target = "result", ignore = true)
    BidResponse toBidResponse(Bid bid);
}
