package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.StatusRequest;
import com.example.AuctionSite.dto.response.StatusResponse;
import com.example.AuctionSite.entity.Status;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status toStatus(StatusRequest statusRequest);

    StatusResponse toStatusResponse(Status status);

    void toUpdateStatus(@MappingTarget Status status, StatusRequest statusRequest);
}
