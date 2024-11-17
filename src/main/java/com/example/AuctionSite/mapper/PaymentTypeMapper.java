package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.PaymentTypeRequest;
import com.example.AuctionSite.dto.response.PaymentTypeResponse;
import com.example.AuctionSite.entity.PaymentType;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {
    PaymentType toPaymentType(PaymentTypeRequest paymentTypeRequest);

    PaymentTypeResponse toPaymentTypeResponse(PaymentType paymentType);

    void toUpdatePaymentType(@MappingTarget PaymentType paymentType, PaymentTypeRequest paymentTypeRequest);
}
