package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.StepRequest;
import com.example.AuctionSite.dto.response.StepResponse;
import com.example.AuctionSite.entity.Step;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StepMapper {
    Step toStep(StepRequest stepRequest);
    
    StepResponse toStepResponse(Step step);
    
    void toUpdateStep(@MappingTarget Step step, StepRequest stepRequest);
}
