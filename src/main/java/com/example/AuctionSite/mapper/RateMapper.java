package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.RateRequest;
import com.example.AuctionSite.dto.response.RateResponse;
import com.example.AuctionSite.entity.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RateMapper {
    Rate toRate(RateRequest rateRequest);
    
    RateResponse toRateResponse(Rate rate);
    
    void toUpdateRate(@MappingTarget Rate rate, RateRequest rateRequest);
}
