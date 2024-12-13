package com.example.AuctionSite.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.ReceiptRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.PaymentConfirmationResponse;
import com.example.AuctionSite.dto.response.ReceiptResponse;
import com.example.AuctionSite.entity.Receipt;
import com.example.AuctionSite.repository.ReceiptRepository;
import com.example.AuctionSite.service.QRCodeService;
import com.example.AuctionSite.service.ReceiptService;
import com.google.zxing.WriterException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReceiptController {
    ReceiptRepository receiptRepository;
    ReceiptService receiptService;
    QRCodeService qrCodeService;

    @GetMapping("/get_all_receipts")
    ApiResponse<List<ReceiptResponse>> getAllReceipts() {
        return ApiResponse.<List<ReceiptResponse>>builder()
                .result(receiptService.getAllReceipts())
                .build();
    }

    @GetMapping("/get_receipt_by_id/{id}")
    ApiResponse<ReceiptResponse> getReceiptById(@PathVariable("id") Integer id) {
        return ApiResponse.<ReceiptResponse>builder()
                .result(receiptService.getReceiptById(id))
                .build();
    }

    @GetMapping("/get_receipt_by_product_id/{id}")
    ApiResponse<ReceiptResponse> getReceiptByProductId(@PathVariable("id") Integer id) {
        return ApiResponse.<ReceiptResponse>builder()
                .result(receiptService.getReceiptByProductId(id))
                .build();
    }

    @GetMapping("/get_receipt_by_seller_id/{id}")
    ApiResponse<List<ReceiptResponse>> getReceiptBySellerId(@PathVariable("id") String id) {
        return ApiResponse.<List<ReceiptResponse>>builder()
                .result(receiptService.getReceiptBySellerId(id))
                .build();
    }

    @GetMapping("/get_receipt_by_buyer_id/{id}")
    ApiResponse<List<ReceiptResponse>> getReceiptByBuyerId(@PathVariable("id") String id) {
        return ApiResponse.<List<ReceiptResponse>>builder()
                .result(receiptService.getReceiptByBuyerId(id))
                .build();
    }

    @GetMapping("/get_receipts_of_seller")
    ApiResponse<List<ReceiptResponse>> getReceiptsOfSeller() {
        return ApiResponse.<List<ReceiptResponse>>builder()
                .result(receiptService.getReceiptsOfSeller())
                .build();
    }

    @GetMapping("/get_receipts_of_buyer")
    ApiResponse<List<ReceiptResponse>> getReceiptsOfBuyer() {
        return ApiResponse.<List<ReceiptResponse>>builder()
                .result(receiptService.getReceiptsOfBuyer())
                .build();
    }

    @PutMapping("/update_receipt/{id}")
    ApiResponse<ReceiptResponse> updateReceipt(
            @PathVariable("id") Integer id, @RequestBody ReceiptRequest receiptRequest) {
        return ApiResponse.<ReceiptResponse>builder()
                .result(receiptService.updateReceipt(id, receiptRequest))
                .build();
    }

    @DeleteMapping("/delete_receipt/{id}")
    ApiResponse<String> deleteReceipt(@PathVariable("id") Integer id) {
        receiptService.deleteReceipt(id);
        return ApiResponse.<String>builder().result("Receipt deleted").build();
    }

    @PostMapping("/payment_confirm_success")
    ApiResponse<PaymentConfirmationResponse> paymentConfirmSuccess(@RequestParam Integer receiptId) {
        return ApiResponse.<PaymentConfirmationResponse>builder()
                .result(receiptService.paymentConfirmSuccess(receiptId))
                .build();
    }

    @PostMapping("/payment_confirm_failure")
    ApiResponse<PaymentConfirmationResponse> paymentConfirmFailure(@RequestParam Integer receiptId) {
        return ApiResponse.<PaymentConfirmationResponse>builder()
                .result(receiptService.paymentConfirmFailure(receiptId))
                .build();
    }

    @GetMapping("/get_receipt_by_id_of_buyer")
    ApiResponse<ReceiptResponse> getReceiptByIdOfBuyer(@RequestParam Integer receiptId) {
        return ApiResponse.<ReceiptResponse>builder()
                .result(receiptService.getReceiptByIdOfBuyer(receiptId))
                .build();
    }

    @GetMapping("/get_receipt_by_id_of_seller")
    ApiResponse<ReceiptResponse> getReceiptByIdOfSeller(@RequestParam Integer receiptId) {
        return ApiResponse.<ReceiptResponse>builder()
                .result(receiptService.getReceiptByIdOfSeller(receiptId))
                .build();
    }

    @GetMapping("/qr-code/{id}")
    public ResponseEntity<byte[]> generateQRCodeForReceipt(@PathVariable Integer id)
            throws WriterException, IOException {
        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new RuntimeException("Receipt not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        String formattedReceiptTime = receipt.getReceiptTime().format(formatter);

        String qrData = String.format(
                """
				Receipt ID: %d
				Name: %s
				Product: %s
				Price: %s
				Date: %s
				DeliveryType: %s
				PaymentType: %s""",
                receipt.getId(),
                receipt.getName(),
                receipt.getProduct().getName(),
                receipt.getSellingPrice(),
                formattedReceiptTime,
                receipt.getDeliveryType().getName(),
                receipt.getPaymentType().getName());

        byte[] qrCode = qrCodeService.generateQRCode(qrData, 300, 300);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return ResponseEntity.ok().headers(headers).body(qrCode);
    }
}
