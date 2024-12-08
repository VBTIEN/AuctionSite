package com.example.AuctionSite.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AuctionSite.dto.request.ReceiptRequest;
import com.example.AuctionSite.dto.response.*;
import com.example.AuctionSite.entity.*;
import com.example.AuctionSite.mapper.ReceiptMapper;
import com.example.AuctionSite.repository.*;

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
    StatusRepository statusRepository;
    ProductRepository productRepository;

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private ReceiptResponse convertToReceiptResponse(Receipt receipt) {
        String formattedRemainingTimeToPayment = formatDuration(receipt.getRemainingTimeToPayment());

        return ReceiptResponse.builder()
                .id(receipt.getId())
                .name(receipt.getName())
                .description(receipt.getDescription())
                .sellingPrice(receipt.getSellingPrice())
                .remainingTimeToPayment(formattedRemainingTimeToPayment)
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
                .timeToPayment(TimeResponse.builder()
                        .time(receipt.getTimeToPayment().getTime().toString())
                        .build())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_RECEIPTS')")
    public List<ReceiptResponse> getAllReceipts() {
        List<Receipt> receipts = receiptRepository.findAll();
        return receipts.stream().map(this::convertToReceiptResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPT_BY_ID')")
    public ReceiptResponse getReceiptById(Integer id) {
        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new RuntimeException("Receipt not found"));
        return convertToReceiptResponse(receipt);
    }

    @PreAuthorize("hasAuthority('GET_RECEIPT_BY_PRODUCTID')")
    public ReceiptResponse getReceiptByProductId(Integer productId) {
        Receipt receipt = receiptRepository.findByProductId(productId).orElseThrow();
        return convertToReceiptResponse(receipt);
    }

    @PreAuthorize("hasAuthority('GET_RECEIPTS_BY_SELLERID')")
    public List<ReceiptResponse> getReceiptBySellerId(String sellerId) {
        List<Receipt> receipts = receiptRepository.findAllBySellerId(sellerId);
        return receipts.stream().map(this::convertToReceiptResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_RECEIPTS_BY_BUYERID')")
    public List<ReceiptResponse> getReceiptByBuyerId(String buyerId) {
        List<Receipt> receipts = receiptRepository.findAllByBuyerId(buyerId);
        return receipts.stream().map(this::convertToReceiptResponse).toList();
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

        return convertToReceiptResponse(receipt);
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

    @PreAuthorize("hasAuthority('PAYMENT_CONFIRM_SUCCESS')")
    public PaymentConfirmationResponse paymentConfirmSuccess(Integer id) {
        String userId = userService.getUserId();

        Receipt receipt = receiptRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt with ID " + id + " not found"));

        Status waitingForPaymentStatus = statusRepository
                .findById("WAITING_FOR_PAYMENT")
                .orElseThrow(() -> new RuntimeException("Status WAITING_FOR_PAYMENT not found"));

        if (!receipt.getStatus().equals(waitingForPaymentStatus)) {
            throw new IllegalStateException(
                    "This receipt cannot be confirmed as it is not in the 'WAITING_FOR_PAYMENT' status.");
        }

        if (!receipt.getBuyer().getId().equals(userId)) {
            throw new AccessDeniedException("You are not authorized to confirm this payment.");
        }

        Status paidStatus =
                statusRepository.findById("PAID").orElseThrow(() -> new RuntimeException("Status PAID not found"));

        receipt.setStatus(paidStatus);
        receiptRepository.save(receipt);

        Product product = receipt.getProduct();
        product.setStatus(
                statusRepository.findById("SOLD").orElseThrow(() -> new RuntimeException("Status SOLD not found")));
        productRepository.save(product);

        String confirmationMessage = String.format(
                "Payment confirmed success for receipt ID %d. Product '%s' is now sold.",
                receipt.getId(), product.getName());

        return new PaymentConfirmationResponse(confirmationMessage);
    }

    @PreAuthorize("hasAuthority('PAYMENT_CONFIRM_FAILURE')")
    public PaymentConfirmationResponse paymentConfirmFailure(Integer id) {
        String userId = userService.getUserId();

        Receipt receipt = receiptRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt with ID " + id + " not found"));

        Status waitingForPaymentStatus = statusRepository
                .findById("WAITING_FOR_PAYMENT")
                .orElseThrow(() -> new RuntimeException("Status WAITING_FOR_PAYMENT not found"));

        if (!receipt.getStatus().equals(waitingForPaymentStatus)) {
            throw new IllegalStateException(
                    "This receipt cannot be confirmed as it is not in the 'WAITING_FOR_PAYMENT' status.");
        }

        if (!receipt.getBuyer().getId().equals(userId)) {
            throw new AccessDeniedException("You are not authorized to confirm this payment.");
        }

        Status paidStatus =
                statusRepository.findById("CANCELLED").orElseThrow(() -> new RuntimeException("Status PAID not found"));

        receipt.setStatus(paidStatus);
        receiptRepository.save(receipt);

        Product product = receipt.getProduct();
        product.setStatus(
                statusRepository.findById("UNSOLD").orElseThrow(() -> new RuntimeException("Status SOLD not found")));
        productRepository.save(product);

        String confirmationMessage = String.format(
                "Payment confirmed failure for receipt ID %d. Product '%s' is now unsold.",
                receipt.getId(), product.getName());

        return new PaymentConfirmationResponse(confirmationMessage);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateReceiptsAndProducts() {
        LocalDateTime now = LocalDateTime.now();

        Status waitingForPaymentStatus = statusRepository
                .findById("WAITING_FOR_PAYMENT")
                .orElseThrow(() -> new RuntimeException("Status WAITING_FOR_PAYMENT not found"));

        Status cancelledStatus = statusRepository
                .findById("CANCELLED")
                .orElseThrow(() -> new RuntimeException("Status CANCELLED not found"));

        Status unsoldStatus =
                statusRepository.findById("UNSOLD").orElseThrow(() -> new RuntimeException("Status UNSOLD not found"));

        List<Receipt> waitingReceipts = receiptRepository.findAllByStatus(waitingForPaymentStatus);

        for (Receipt receipt : waitingReceipts) {
            Duration totalPaymentTime = receipt.getTimeToPayment().getTime();
            Duration elapsedTime = Duration.between(receipt.getReceiptTime(), now);
            Duration newRemainingTime = totalPaymentTime.minus(elapsedTime);

            if (!newRemainingTime.isNegative() && !newRemainingTime.isZero()) {
                receipt.setRemainingTimeToPayment(newRemainingTime);
            } else {
                receipt.setStatus(cancelledStatus);
                receipt.setRemainingTimeToPayment(Duration.ZERO);

                Product product = receipt.getProduct();
                if (product != null) {
                    product.setStatus(unsoldStatus);
                    productRepository.save(product);
                    log.info("Product {} has been marked as UNSOLD due to receipt timeout", product.getId());
                }

                log.info("Receipt {} has been CANCELLED due to timeout", receipt.getId());
            }
        }

        receiptRepository.saveAll(waitingReceipts);
    }
    
    @PreAuthorize("hasAuthority('GET_RECEIPTS_OF_SELLER')")
    public List<ReceiptResponse> getReceiptsOfSeller() {
        String userId = userService.getUserId();
        
        List<Receipt> receipts = receiptRepository.findAllBySellerId(userId);
        return receipts.stream().map(this::convertToReceiptResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('GET_RECEIPTS_OF_BUYER')")
    public List<ReceiptResponse> getReceiptsOfBuyer() {
        String userId = userService.getUserId();
        
        List<Receipt> receipts = receiptRepository.findAllByBuyerId(userId);
        return receipts.stream().map(this::convertToReceiptResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('GET_RECEIPT_BY_ID_OF_BUYER')")
    public ReceiptResponse getReceiptByIdOfBuyer(Integer receiptId) {
        // Lấy ID người dùng hiện tại
        String currentUserId = userService.getUserId();
        
        // Tìm hóa đơn theo ID
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Receipt not found"));
        
        // Kiểm tra nếu người mua của hóa đơn không phải là người dùng hiện tại
        if (!receipt.getBuyer().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You are not authorized to access this receipt");
        }
        
        // Chuyển đổi và trả về ReceiptResponse
        return convertToReceiptResponse(receipt);
    }
    
    @PreAuthorize("hasAuthority('GET_RECEIPT_BY_ID_OF_SELLER')")
    public ReceiptResponse getReceiptByIdOfSeller(Integer receiptId) {
        // Lấy ID người dùng hiện tại
        String currentUserId = userService.getUserId();
        
        // Tìm hóa đơn theo ID
        Receipt receipt = receiptRepository.findById(receiptId)
            .orElseThrow(() -> new RuntimeException("Receipt not found"));
        
        // Kiểm tra nếu người mua của hóa đơn không phải là người dùng hiện tại
        if (!receipt.getSeller().getId().equals(currentUserId)) {
            throw new AccessDeniedException("You are not authorized to access this receipt");
        }
        
        // Chuyển đổi và trả về ReceiptResponse
        return convertToReceiptResponse(receipt);
    }
}
