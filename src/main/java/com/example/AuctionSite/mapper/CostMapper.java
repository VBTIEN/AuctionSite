package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.CostRequest;
import com.example.AuctionSite.dto.response.CostResponse;
import com.example.AuctionSite.entity.Cost;

@Mapper(componentModel = "spring")
public interface CostMapper {
    Cost toCost(CostRequest costRequest);

    CostResponse toCostResponse(Cost cost);

    void toUpdateCost(@MappingTarget Cost cost, CostRequest costRequest);
}
