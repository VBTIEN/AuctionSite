package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.DeliveryTypeRequest;
import com.example.AuctionSite.dto.response.DeliveryTypeResponse;
import com.example.AuctionSite.entity.DeliveryType;
import com.example.AuctionSite.mapper.DeliveryTypeMapper;
import com.example.AuctionSite.repository.DeliveryTypeRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryTypeService {
    DeliveryTypeRepository deliveryTypeRepository;
    DeliveryTypeMapper deliveryTypeMapper;

    @PreAuthorize("hasAuthority('CREATE_DELIVERYTYPE')")
    public DeliveryTypeResponse createDeliveryType(DeliveryTypeRequest deliveryTypeRequest) {
        DeliveryType deliveryType = deliveryTypeMapper.toDeliveryType(deliveryTypeRequest);
        return deliveryTypeMapper.toDeliveryTypeResponse(deliveryTypeRepository.save(deliveryType));
    }

    @PreAuthorize("hasAuthority('GET_ALL_DELIVERYTYPES')")
    public List<DeliveryTypeResponse> getAllDeliveryTypes() {
        return deliveryTypeRepository.findAll().stream()
                .map(deliveryTypeMapper::toDeliveryTypeResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_DELIVERYTYPE_BY_NAME')")
    public DeliveryTypeResponse getDeliveryTypeByName(String name) {
        DeliveryType deliveryType = deliveryTypeRepository.findById(name).orElseThrow();
        return deliveryTypeMapper.toDeliveryTypeResponse(deliveryType);
    }

    @PreAuthorize("hasAuthority('UPDATE_DELIVERYTYPE')")
    public DeliveryTypeResponse updateDeliveryType(String name, DeliveryTypeRequest deliveryTypeRequest) {
        DeliveryType deliveryType = deliveryTypeRepository.findById(name).orElseThrow();
        deliveryTypeMapper.toUpdateDeliveryType(deliveryType, deliveryTypeRequest);
        return deliveryTypeMapper.toDeliveryTypeResponse(deliveryTypeRepository.save(deliveryType));
    }

    @PreAuthorize("hasAuthority('DELETE_DELIVERYTYPE')")
    public void deleteDeliveryType(String name) {
        deliveryTypeRepository.deleteById(name);
    }
}
