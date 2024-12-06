package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.ReceiptRequest;
import com.example.AuctionSite.entity.Receipt;

@Mapper(componentModel = "spring")
public interface ReceiptMapper {
    @Mapping(target = "receiptTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "sellingPrice", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "deliveryType", ignore = true)
    @Mapping(target = "paymentType", ignore = true)
    void toUpdateReceipt(@MappingTarget Receipt receipt, ReceiptRequest receiptRequest);
}
