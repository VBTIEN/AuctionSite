package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.TimeRequest;
import com.example.AuctionSite.dto.response.TimeResponse;
import com.example.AuctionSite.entity.Time;

@Mapper(componentModel = "spring")
public interface TimeMapper {
    Time toTime(TimeRequest timeRequest);

    TimeResponse toTimeResponse(Time time);

    void toUpdateTime(@MappingTarget Time time, TimeRequest timeRequest);
}
