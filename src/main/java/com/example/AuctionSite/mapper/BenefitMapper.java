package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.BenefitRequest;
import com.example.AuctionSite.dto.response.BenefitResponse;
import com.example.AuctionSite.entity.Benefit;

@Mapper(componentModel = "spring")
public interface BenefitMapper {
    Benefit toBenefit(BenefitRequest benefitRequest);

    BenefitResponse toBenefitResponse(Benefit benefit);

    void toUpdateBenefit(@MappingTarget Benefit benefit, BenefitRequest benefitRequest);
}
