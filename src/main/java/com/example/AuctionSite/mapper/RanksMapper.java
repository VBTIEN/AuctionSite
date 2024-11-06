package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.RanksRequest;
import com.example.AuctionSite.dto.response.RanksResponse;
import com.example.AuctionSite.entity.Ranks;

@Mapper(componentModel = "spring")
public interface RanksMapper {
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "benefits", ignore = true)
    Ranks toRanks(RanksRequest ranksRequest);

    RanksResponse toRanksResponse(Ranks ranks);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "benefits", ignore = true)
    void toUpdateRank(@MappingTarget Ranks ranks, RanksRequest ranksRequest);
}
