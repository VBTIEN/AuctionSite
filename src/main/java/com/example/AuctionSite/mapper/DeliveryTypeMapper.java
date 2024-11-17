package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.DeliveryTypeRequest;
import com.example.AuctionSite.dto.response.DeliveryTypeResponse;
import com.example.AuctionSite.entity.DeliveryType;

@Mapper(componentModel = "spring")
public interface DeliveryTypeMapper {
    DeliveryType toDeliveryType(DeliveryTypeRequest deliveryTypeRequest);

    DeliveryTypeResponse toDeliveryTypeResponse(DeliveryType deliveryType);

    void toUpdateDeliveryType(@MappingTarget DeliveryType deliveryType, DeliveryTypeRequest deliveryTypeRequest);
}
