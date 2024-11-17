package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.PaymentTypeRequest;
import com.example.AuctionSite.dto.response.PaymentTypeResponse;
import com.example.AuctionSite.entity.PaymentType;
import com.example.AuctionSite.mapper.PaymentTypeMapper;
import com.example.AuctionSite.repository.PaymentTypeRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentTypeService {
    PaymentTypeRepository paymentTypeRepository;
    PaymentTypeMapper paymentTypeMapper;

    @PreAuthorize("hasAuthority('CREATE_PAYMENTTYPE')")
    public PaymentTypeResponse createPaymentType(PaymentTypeRequest paymentTypeRequest) {
        PaymentType paymentType = paymentTypeMapper.toPaymentType(paymentTypeRequest);
        return paymentTypeMapper.toPaymentTypeResponse(paymentTypeRepository.save(paymentType));
    }

    @PreAuthorize("hasAuthority('GET_ALL_PAYMENTTYPES')")
    public List<PaymentTypeResponse> getAllPaymentTypes() {
        return paymentTypeRepository.findAll().stream()
                .map(paymentTypeMapper::toPaymentTypeResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_PAYMENTTYPE_BY_NAME')")
    public PaymentTypeResponse getPaymentTypeByName(String name) {
        PaymentType paymentType = paymentTypeRepository.findById(name).orElseThrow();
        return paymentTypeMapper.toPaymentTypeResponse(paymentType);
    }

    @PreAuthorize("hasAuthority('UPDATE_PAYMENTTYPE')")
    public PaymentTypeResponse updatePaymentType(String name, PaymentTypeRequest paymentTypeRequest) {
        PaymentType paymentType = paymentTypeRepository.findById(name).orElseThrow();
        paymentTypeMapper.toUpdatePaymentType(paymentType, paymentTypeRequest);
        return paymentTypeMapper.toPaymentTypeResponse(paymentTypeRepository.save(paymentType));
    }

    @PreAuthorize("hasAuthority('DELETE_PAYMENTTYPE')")
    public void deletePaymentType(String name) {
        paymentTypeRepository.deleteById(name);
    }
}
