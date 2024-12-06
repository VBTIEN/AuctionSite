package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.PaymentTypeRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.PaymentTypeResponse;
import com.example.AuctionSite.service.PaymentTypeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment_types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentTypeController {
    PaymentTypeService paymentTypeService;

    @PostMapping("/create_payment_type")
    ApiResponse<PaymentTypeResponse> createPaymentType(@Valid @RequestBody PaymentTypeRequest paymentTypeRequest) {
        return ApiResponse.<PaymentTypeResponse>builder()
                .result(paymentTypeService.createPaymentType(paymentTypeRequest))
                .build();
    }

    @GetMapping("/get_all_payment_types")
    ApiResponse<List<PaymentTypeResponse>> getAllPaymentTypes() {
        return ApiResponse.<List<PaymentTypeResponse>>builder()
                .result(paymentTypeService.getAllPaymentTypes())
                .build();
    }

    @GetMapping("/get_payment_type_by_name/{name}")
    ApiResponse<PaymentTypeResponse> getPaymentTypeByName(@PathVariable("name") String name) {
        return ApiResponse.<PaymentTypeResponse>builder()
                .result(paymentTypeService.getPaymentTypeByName(name))
                .build();
    }

    @PutMapping("/update_payment_type/{name}")
    ApiResponse<PaymentTypeResponse> updatePaymentType(
            @PathVariable("name") String name, @RequestBody PaymentTypeRequest paymentTypeRequest) {
        return ApiResponse.<PaymentTypeResponse>builder()
                .result(paymentTypeService.updatePaymentType(name, paymentTypeRequest))
                .build();
    }

    @DeleteMapping("/delete_payment_type/{name}")
    ApiResponse<String> deletePaymentType(@PathVariable("name") String name) {
        paymentTypeService.deletePaymentType(name);
        return ApiResponse.<String>builder().result("Payment type deleted").build();
    }
}
