package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.request.StatusRequest;
import com.example.AuctionSite.dto.response.StatusResponse;
import com.example.AuctionSite.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status toStatus(StatusRequest statusRequest);
    
    StatusResponse toStatusResponse(Status status);
    
    void toUpdateStatus(@MappingTarget Status status, StatusRequest statusRequest);
}
