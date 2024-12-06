package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.DeliveryTypeRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.DeliveryTypeResponse;
import com.example.AuctionSite.service.DeliveryTypeService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/delivery_types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DeliveryTypeController {
    DeliveryTypeService deliveryTypeService;

    @PostMapping("/create_delivery_type")
    ApiResponse<DeliveryTypeResponse> createDeliveryType(@Valid @RequestBody DeliveryTypeRequest deliveryTypeRequest) {
        return ApiResponse.<DeliveryTypeResponse>builder()
                .result(deliveryTypeService.createDeliveryType(deliveryTypeRequest))
                .build();
    }

    @GetMapping("/get_all_delivery_types")
    ApiResponse<List<DeliveryTypeResponse>> getAllDeliveryTypes() {
        return ApiResponse.<List<DeliveryTypeResponse>>builder()
                .result(deliveryTypeService.getAllDeliveryTypes())
                .build();
    }

    @GetMapping("/get_delivery_type_by_name/{name}")
    ApiResponse<DeliveryTypeResponse> getDeliveryTypeByName(@PathVariable("name") String name) {
        return ApiResponse.<DeliveryTypeResponse>builder()
                .result(deliveryTypeService.getDeliveryTypeByName(name))
                .build();
    }

    @PutMapping("/update_delivery_type/{name}")
    ApiResponse<DeliveryTypeResponse> updateDeliveryType(
            @PathVariable("name") String name, @RequestBody DeliveryTypeRequest deliveryTypeRequest) {
        return ApiResponse.<DeliveryTypeResponse>builder()
                .result(deliveryTypeService.updateDeliveryType(name, deliveryTypeRequest))
                .build();
    }

    @DeleteMapping("/delete_delivery_type/{name}")
    ApiResponse<String> deleteDeliveryType(@PathVariable("name") String name) {
        deliveryTypeService.deleteDeliveryType(name);
        return ApiResponse.<String>builder().result("Delivery type deleted").build();
    }
}
