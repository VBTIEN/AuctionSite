package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.RegulationRequest;
import com.example.AuctionSite.dto.response.RegulationResponse;
import com.example.AuctionSite.entity.Regulation;

@Mapper(componentModel = "spring")
public interface RegulationMapper {
    Regulation toRegulation(RegulationRequest regulationRequest);

    RegulationResponse toRegulationResponse(Regulation regulation);

    void toUpdateRegulation(@MappingTarget Regulation regulation, RegulationRequest regulationRequest);
}
