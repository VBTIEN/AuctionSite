package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.RateRequest;
import com.example.AuctionSite.dto.response.RateResponse;
import com.example.AuctionSite.entity.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RateMapper {
    @Mapping(target = "users", ignore = true)
    Rate toRate(RateRequest rateRequest);
    
    RateResponse toRateResponse(Rate rate);
    
    @Mapping(target = "users", ignore = true)
    void toUpdateRate(@MappingTarget Rate rate, RateRequest rateRequest);
}