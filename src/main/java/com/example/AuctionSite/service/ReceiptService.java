package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.ReceiptRequest;
import com.example.AuctionSite.dto.response.*;
import com.example.AuctionSite.entity.DeliveryType;
import com.example.AuctionSite.entity.PaymentType;
import com.example.AuctionSite.entity.Receipt;
import com.example.AuctionSite.entity.User;
import com.example.AuctionSite.mapper.ReceiptMapper;
import com.example.AuctionSite.repository.DeliveryTypeRepository;
import com.example.AuctionSite.repository.PaymentTypeRepository;
import com.example.AuctionSite.repository.ReceiptRepository;
import com.example.AuctionSite.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiptService {
    ReceiptRepository receiptRepository;
    ReceiptMapper receiptMapper;
    UserService userService;
    UserRepository userRepository;
    DeliveryTypeRepository deliveryTypeRepository;
    PaymentTypeRepository paymentTypeRepository;
    JdbcTemplate jdbcTemplate;

    @PreAuthorize("hasAuthority('GET_ALL_RECEIPTS')")
    public List<ReceiptResponse> getAllReceipts() {
        List<Receipt> receipts = receiptRepository.findAll();
        return receipts.stream()
                .map(receipt -> ReceiptResponse.builder()
                        .id(receipt.getId())
                        .name(receipt.getName())
                        .description(receipt.getDescription())
                        .sellingPrice(receipt.getSellingPrice())
                        .receiptTime(receipt.getReceiptTime())
                        .product(ProductResponse.builder()
                                .id(receipt.getProduct().getId())
                                .name(receipt.getProduct().getName())
                                .build())
                        .seller(UserResponse.builder()
                                .id(receipt.getSeller().getId())
                                .username(receipt.getSeller().getUsername())
                                .fullName(receipt.getSeller().getFullName())
                                .email(receipt.getSeller().getEmail())
                                .phoneNumber(receipt.getSeller().getPhoneNumber())
                                .address(receipt.getSeller().getAddress())
                                .build())
                        .buyer(UserResponse.builder()
                                .id(receipt.getBuyer().getId())
                                .username(receipt.getBuyer().getUsername())
                                .fullName(receipt.getBuyer().getFullName())
                                .email(receipt.getBuyer().getEmail())
                                .phoneNumber(receipt.getBuyer().getPhoneNumber())
                                .address(receipt.getBuyer().getAddress())
                                .build())
                        .deliveryType(DeliveryTypeResponse.builder()
                                .name(receipt.getDeliveryType().getName())
                                .description(receipt.getDeliveryType().getDescription())
                                .build())
                        .paymentType(PaymentTypeResponse.builder()
                                .name(receipt.getPaymentType().getName())
                                .description(receipt.getPaymentType().getDescription())
                                .build())
                        .status(StatusResponse.builder()
                                .name(receipt.getStatus().getName())
                                .description(receipt.getStatus().getDescription())
                                .build())
                        .build())
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPT_BY_ID')")
    public ReceiptResponse getReceiptById(Integer id) {
        Receipt receipt = receiptRepository.findById(id).orElseThrow();
        return ReceiptResponse.builder()
                .id(receipt.getId())
                .name(receipt.getName())
                .description(receipt.getDescription())
                .sellingPrice(receipt.getSellingPrice())
                .receiptTime(receipt.getReceiptTime())
                .product(ProductResponse.builder()
                        .id(receipt.getProduct().getId())
                        .name(receipt.getProduct().getName())
                        .build())
                .seller(UserResponse.builder()
                        .id(receipt.getSeller().getId())
                        .username(receipt.getSeller().getUsername())
                        .fullName(receipt.getSeller().getFullName())
                        .email(receipt.getSeller().getEmail())
                        .phoneNumber(receipt.getSeller().getPhoneNumber())
                        .address(receipt.getSeller().getAddress())
                        .build())
                .buyer(UserResponse.builder()
                        .id(receipt.getBuyer().getId())
                        .username(receipt.getBuyer().getUsername())
                        .fullName(receipt.getBuyer().getFullName())
                        .email(receipt.getBuyer().getEmail())
                        .phoneNumber(receipt.getBuyer().getPhoneNumber())
                        .address(receipt.getBuyer().getAddress())
                        .build())
                .deliveryType(DeliveryTypeResponse.builder()
                        .name(receipt.getDeliveryType().getName())
                        .description(receipt.getDeliveryType().getDescription())
                        .build())
                .paymentType(PaymentTypeResponse.builder()
                        .name(receipt.getPaymentType().getName())
                        .description(receipt.getPaymentType().getDescription())
                        .build())
                .status(StatusResponse.builder()
                        .name(receipt.getStatus().getName())
                        .description(receipt.getStatus().getDescription())
                        .build())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPT_BY_PRODUCTID')")
    public ReceiptResponse getReceiptByProductId(Integer productId) {
        Receipt receipt = receiptRepository.findByProductId(productId).orElseThrow();
        return ReceiptResponse.builder()
                .id(receipt.getId())
                .name(receipt.getName())
                .description(receipt.getDescription())
                .sellingPrice(receipt.getSellingPrice())
                .receiptTime(receipt.getReceiptTime())
                .product(ProductResponse.builder()
                        .id(receipt.getProduct().getId())
                        .name(receipt.getProduct().getName())
                        .build())
                .seller(UserResponse.builder()
                        .id(receipt.getSeller().getId())
                        .username(receipt.getSeller().getUsername())
                        .fullName(receipt.getSeller().getFullName())
                        .email(receipt.getSeller().getEmail())
                        .phoneNumber(receipt.getSeller().getPhoneNumber())
                        .address(receipt.getSeller().getAddress())
                        .build())
                .buyer(UserResponse.builder()
                        .id(receipt.getBuyer().getId())
                        .username(receipt.getBuyer().getUsername())
                        .fullName(receipt.getBuyer().getFullName())
                        .email(receipt.getBuyer().getEmail())
                        .phoneNumber(receipt.getBuyer().getPhoneNumber())
                        .address(receipt.getBuyer().getAddress())
                        .build())
                .deliveryType(DeliveryTypeResponse.builder()
                        .name(receipt.getDeliveryType().getName())
                        .description(receipt.getDeliveryType().getDescription())
                        .build())
                .paymentType(PaymentTypeResponse.builder()
                        .name(receipt.getPaymentType().getName())
                        .description(receipt.getPaymentType().getDescription())
                        .build())
                .status(StatusResponse.builder()
                        .name(receipt.getStatus().getName())
                        .description(receipt.getStatus().getDescription())
                        .build())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPTS_BY_SELLERID')")
    public List<ReceiptResponse> getReceiptBySellerId(String sellerId) {
        List<Receipt> receipts = receiptRepository.findAllBySellerId(sellerId);
        return receipts.stream()
                .map(receipt -> ReceiptResponse.builder()
                        .id(receipt.getId())
                        .name(receipt.getName())
                        .description(receipt.getDescription())
                        .sellingPrice(receipt.getSellingPrice())
                        .receiptTime(receipt.getReceiptTime())
                        .product(ProductResponse.builder()
                                .id(receipt.getProduct().getId())
                                .name(receipt.getProduct().getName())
                                .build())
                        .seller(UserResponse.builder()
                                .id(receipt.getSeller().getId())
                                .username(receipt.getSeller().getUsername())
                                .fullName(receipt.getSeller().getFullName())
                                .email(receipt.getSeller().getEmail())
                                .phoneNumber(receipt.getSeller().getPhoneNumber())
                                .address(receipt.getSeller().getAddress())
                                .build())
                        .buyer(UserResponse.builder()
                                .id(receipt.getBuyer().getId())
                                .username(receipt.getBuyer().getUsername())
                                .fullName(receipt.getBuyer().getFullName())
                                .email(receipt.getBuyer().getEmail())
                                .phoneNumber(receipt.getBuyer().getPhoneNumber())
                                .address(receipt.getBuyer().getAddress())
                                .build())
                        .deliveryType(DeliveryTypeResponse.builder()
                                .name(receipt.getDeliveryType().getName())
                                .description(receipt.getDeliveryType().getDescription())
                                .build())
                        .paymentType(PaymentTypeResponse.builder()
                                .name(receipt.getPaymentType().getName())
                                .description(receipt.getPaymentType().getDescription())
                                .build())
                        .status(StatusResponse.builder()
                                .name(receipt.getStatus().getName())
                                .description(receipt.getStatus().getDescription())
                                .build())
                        .build())
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPTS_BY_BUYERID')")
    public List<ReceiptResponse> getReceiptByBuyerId(String buyerId) {
        List<Receipt> receipts = receiptRepository.findAllByBuyerId(buyerId);
        return receipts.stream()
                .map(receipt -> ReceiptResponse.builder()
                        .id(receipt.getId())
                        .name(receipt.getName())
                        .description(receipt.getDescription())
                        .sellingPrice(receipt.getSellingPrice())
                        .receiptTime(receipt.getReceiptTime())
                        .product(ProductResponse.builder()
                                .id(receipt.getProduct().getId())
                                .name(receipt.getProduct().getName())
                                .build())
                        .seller(UserResponse.builder()
                                .id(receipt.getSeller().getId())
                                .username(receipt.getSeller().getUsername())
                                .fullName(receipt.getSeller().getFullName())
                                .email(receipt.getSeller().getEmail())
                                .phoneNumber(receipt.getSeller().getPhoneNumber())
                                .address(receipt.getSeller().getAddress())
                                .build())
                        .buyer(UserResponse.builder()
                                .id(receipt.getBuyer().getId())
                                .username(receipt.getBuyer().getUsername())
                                .fullName(receipt.getBuyer().getFullName())
                                .email(receipt.getBuyer().getEmail())
                                .phoneNumber(receipt.getBuyer().getPhoneNumber())
                                .address(receipt.getBuyer().getAddress())
                                .build())
                        .deliveryType(DeliveryTypeResponse.builder()
                                .name(receipt.getDeliveryType().getName())
                                .description(receipt.getDeliveryType().getDescription())
                                .build())
                        .paymentType(PaymentTypeResponse.builder()
                                .name(receipt.getPaymentType().getName())
                                .description(receipt.getPaymentType().getDescription())
                                .build())
                        .status(StatusResponse.builder()
                                .name(receipt.getStatus().getName())
                                .description(receipt.getStatus().getDescription())
                                .build())
                        .build())
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPTS_OF_SELLER')")
    public List<ReceiptResponse> getReceiptsOfSeller() {
        String userId = userService.getUserId();

        List<Receipt> receipts = receiptRepository.findAllBySellerId(userId);
        return receipts.stream()
                .map(receipt -> ReceiptResponse.builder()
                        .id(receipt.getId())
                        .name(receipt.getName())
                        .description(receipt.getDescription())
                        .sellingPrice(receipt.getSellingPrice())
                        .receiptTime(receipt.getReceiptTime())
                        .product(ProductResponse.builder()
                                .id(receipt.getProduct().getId())
                                .name(receipt.getProduct().getName())
                                .build())
                        .seller(UserResponse.builder()
                                .id(receipt.getSeller().getId())
                                .username(receipt.getSeller().getUsername())
                                .fullName(receipt.getSeller().getFullName())
                                .email(receipt.getSeller().getEmail())
                                .phoneNumber(receipt.getSeller().getPhoneNumber())
                                .address(receipt.getSeller().getAddress())
                                .build())
                        .buyer(UserResponse.builder()
                                .id(receipt.getBuyer().getId())
                                .username(receipt.getBuyer().getUsername())
                                .fullName(receipt.getBuyer().getFullName())
                                .email(receipt.getBuyer().getEmail())
                                .phoneNumber(receipt.getBuyer().getPhoneNumber())
                                .address(receipt.getBuyer().getAddress())
                                .build())
                        .deliveryType(DeliveryTypeResponse.builder()
                                .name(receipt.getDeliveryType().getName())
                                .description(receipt.getDeliveryType().getDescription())
                                .build())
                        .paymentType(PaymentTypeResponse.builder()
                                .name(receipt.getPaymentType().getName())
                                .description(receipt.getPaymentType().getDescription())
                                .build())
                        .status(StatusResponse.builder()
                                .name(receipt.getStatus().getName())
                                .description(receipt.getStatus().getDescription())
                                .build())
                        .build())
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPTS_OF_BUYER')")
    public List<ReceiptResponse> getReceiptsOfBuyer() {
        String userId = userService.getUserId();

        List<Receipt> receipts = receiptRepository.findAllByBuyerId(userId);
        return receipts.stream()
                .map(receipt -> ReceiptResponse.builder()
                        .id(receipt.getId())
                        .name(receipt.getName())
                        .description(receipt.getDescription())
                        .sellingPrice(receipt.getSellingPrice())
                        .receiptTime(receipt.getReceiptTime())
                        .product(ProductResponse.builder()
                                .id(receipt.getProduct().getId())
                                .name(receipt.getProduct().getName())
                                .build())
                        .seller(UserResponse.builder()
                                .id(receipt.getSeller().getId())
                                .username(receipt.getSeller().getUsername())
                                .fullName(receipt.getSeller().getFullName())
                                .email(receipt.getSeller().getEmail())
                                .phoneNumber(receipt.getSeller().getPhoneNumber())
                                .address(receipt.getSeller().getAddress())
                                .build())
                        .buyer(UserResponse.builder()
                                .id(receipt.getBuyer().getId())
                                .username(receipt.getBuyer().getUsername())
                                .fullName(receipt.getBuyer().getFullName())
                                .email(receipt.getBuyer().getEmail())
                                .phoneNumber(receipt.getBuyer().getPhoneNumber())
                                .address(receipt.getBuyer().getAddress())
                                .build())
                        .deliveryType(DeliveryTypeResponse.builder()
                                .name(receipt.getDeliveryType().getName())
                                .description(receipt.getDeliveryType().getDescription())
                                .build())
                        .paymentType(PaymentTypeResponse.builder()
                                .name(receipt.getPaymentType().getName())
                                .description(receipt.getPaymentType().getDescription())
                                .build())
                        .status(StatusResponse.builder()
                                .name(receipt.getStatus().getName())
                                .description(receipt.getStatus().getDescription())
                                .build())
                        .build())
                .toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_RECEIPT')")
    public ReceiptResponse updateReceipt(Integer id, ReceiptRequest receiptRequest) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        boolean isAdmin =
                user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new RuntimeException("Receipt not found"));

        if (!isAdmin) {
            boolean isSeller = receipt.getSeller().getId().equals(userId);
            boolean isBuyer = receipt.getBuyer().getId().equals(userId);

            if (!isSeller && !isBuyer) {
                throw new RuntimeException("Access denied: You are not authorized to update this receipt");
            }
        }

        DeliveryType deliveryType = deliveryTypeRepository
                .findById(receiptRequest.getDeliveryType())
                .orElseThrow();
        PaymentType paymentType =
                paymentTypeRepository.findById(receiptRequest.getPaymentType()).orElseThrow();

        receiptMapper.toUpdateReceipt(receipt, receiptRequest);
        receipt.setDeliveryType(deliveryType);
        receipt.setPaymentType(paymentType);

        receiptRepository.save(receipt);

        return ReceiptResponse.builder()
                .id(receipt.getId())
                .name(receipt.getName())
                .description(receipt.getDescription())
                .sellingPrice(receipt.getSellingPrice())
                .receiptTime(receipt.getReceiptTime())
                .product(ProductResponse.builder()
                        .id(receipt.getProduct().getId())
                        .name(receipt.getProduct().getName())
                        .build())
                .seller(UserResponse.builder()
                        .id(receipt.getSeller().getId())
                        .username(receipt.getSeller().getUsername())
                        .fullName(receipt.getSeller().getFullName())
                        .email(receipt.getSeller().getEmail())
                        .phoneNumber(receipt.getSeller().getPhoneNumber())
                        .address(receipt.getSeller().getAddress())
                        .build())
                .buyer(UserResponse.builder()
                        .id(receipt.getBuyer().getId())
                        .username(receipt.getBuyer().getUsername())
                        .fullName(receipt.getBuyer().getFullName())
                        .email(receipt.getBuyer().getEmail())
                        .phoneNumber(receipt.getBuyer().getPhoneNumber())
                        .address(receipt.getBuyer().getAddress())
                        .build())
                .deliveryType(DeliveryTypeResponse.builder()
                        .name(receipt.getDeliveryType().getName())
                        .description(receipt.getDeliveryType().getDescription())
                        .build())
                .paymentType(PaymentTypeResponse.builder()
                        .name(receipt.getPaymentType().getName())
                        .description(receipt.getPaymentType().getDescription())
                        .build())
                .status(StatusResponse.builder()
                        .name(receipt.getStatus().getName())
                        .description(receipt.getStatus().getDescription())
                        .build())
                .build();
    }

    @PreAuthorize("hasAuthority('DELETE_RECEIPT')")
    public void deleteReceipt(Integer id) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        boolean isAdmin =
                user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new RuntimeException("Receipt not found"));

        if (!isAdmin) {
            boolean isSeller = receipt.getSeller().getId().equals(userId);

            if (!isSeller) {
                throw new RuntimeException("Access denied: You are not authorized to update this receipt");
            }
        }
        String deleteUserSaleReceiptQuery = "DELETE FROM user_sales_receipt WHERE sales_receipt_id = ?";
        jdbcTemplate.update(deleteUserSaleReceiptQuery, id);

        String deleteUserPurchaseReceiptQuery = "DELETE FROM user_purchase_receipt WHERE purchase_receipt_id = ?";
        jdbcTemplate.update(deleteUserPurchaseReceiptQuery, id);

        receiptRepository.deleteById(id);
    }
}
