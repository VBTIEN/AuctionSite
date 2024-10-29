package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.BenefitRequest;
import com.example.AuctionSite.dto.response.BenefitResponse;
import com.example.AuctionSite.entity.Benefit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BenefitMapper {
    Benefit toBenefit(BenefitRequest benefitRequest);
    
    BenefitResponse toBenefitResponse(Benefit benefit);
    
    void toUpdateBenefit(@MappingTarget Benefit benefit, BenefitRequest benefitRequest);
}
