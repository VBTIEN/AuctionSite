package com.example.AuctionSite.controller;

import java.io.IOException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.ProductRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.ProductPageResponse;
import com.example.AuctionSite.dto.response.ProductResponse;
import com.example.AuctionSite.service.ProductService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {
    ProductService productService;

    @PostMapping(value = "/create_product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<ProductResponse> createProduct(@ModelAttribute ProductRequest productRequest)
            throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(productRequest))
                .build();
    }

    @GetMapping("/get_all_products")
    ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .build();
    }

    @GetMapping("/get_product_by_id/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable("id") Integer id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();
    }

    @GetMapping("/get_product_by_name/{name}")
    ApiResponse<List<ProductResponse>> getProductByName(@PathVariable("name") String name) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductByName(name))
                .build();
    }

    @PutMapping(value = "/update_product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<ProductResponse> updateProduct(
            @PathVariable("id") Integer id, @ModelAttribute ProductRequest productRequest) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id, productRequest))
                .build();
    }

    @DeleteMapping("/delete_product/{id}")
    ApiResponse<String> deleteProduct(@PathVariable("id") Integer id) throws IOException {
        productService.deleteProduct(id);
        return ApiResponse.<String>builder().result("Product deleted").build();
    }

    @GetMapping("/search_product_by_name")
    ApiResponse<List<ProductResponse>> searchProductsByName(
            @RequestParam String name, @RequestParam(defaultValue = "30") int threshold) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.searchProductsByName(name, threshold))
                .build();
    }

    @GetMapping("/get_products_pending_auction")
    ApiResponse<List<ProductResponse>> getProductsPendingAuction() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsPendingAuction())
                .build();
    }

    @GetMapping("/get_products_active")
    ApiResponse<List<ProductResponse>> getProductsActive() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductsActive())
                .build();
    }

    @GetMapping("/products_paged")
    ApiResponse<ProductPageResponse> getProductsPaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsPaged(page, size))
                .build();
    }

    @GetMapping("/products_pending_auction_paged")
    ApiResponse<ProductPageResponse> getProductsPendingAuctionPaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsPendingAuctionPaged(page, size))
                .build();
    }

    @GetMapping("/products_active_paged")
    ApiResponse<ProductPageResponse> getAllProductsActivePaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsActivePaged(page, size))
                .build();
    }

    @GetMapping("/products_sold_paged")
    ApiResponse<ProductPageResponse> getAllProductsSoldPaged(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsSoldPaged(page, size))
                .build();
    }

    @GetMapping("/get_all_product_by_userid/{userId}")
    ApiResponse<List<ProductResponse>> getAllProductsByUserId(@PathVariable("userId") String userId) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProductsByUserId(userId))
                .build();
    }

    @GetMapping("/get_all_product_of_user")
    ApiResponse<List<ProductResponse>> getAllProductsOfUser() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProductsOfUser())
                .build();
    }

    @GetMapping("/get_product_by_id_of_user/{id}")
    ApiResponse<ProductResponse> getProductByIdOfUser(@PathVariable("id") Integer id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductByIdOfUser(id))
                .build();
    }

    @GetMapping("/get_product_by_name_of_user/{name}")
    ApiResponse<List<ProductResponse>> getProductByNameOfUser(@PathVariable("name") String name) {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductByNameOfUser(name))
                .build();
    }

    @GetMapping("/products_pending_auction_paged_of_user")
    ApiResponse<ProductPageResponse> getProductsPendingAuctionPagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsPendingAuctionPagedOfUser(page, size))
                .build();
    }

    @GetMapping("/products_active_paged_of_user")
    ApiResponse<ProductPageResponse> getProductsActivePagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsActivePagedOfUser(page, size))
                .build();
    }

    @GetMapping("/products_added_paged_of_user")
    ApiResponse<ProductPageResponse> getProductsAddedPagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsAddedPagedOfUser(page, size))
                .build();
    }

    @GetMapping("/products_sold_paged_of_user")
    ApiResponse<ProductPageResponse> getProductsSoldPagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsSoldPagedOfUser(page, size))
                .build();
    }

    @GetMapping("/products_sa_paged_of_user")
    ApiResponse<ProductPageResponse> getProductsSAPagedOfUser(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getAllProductsSAPagedOfUser(page, size))
                .build();
    }

    @GetMapping("/products_by_id_paged")
    ApiResponse<ProductPageResponse> getProductsByNamePaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String productName) {
        return ApiResponse.<ProductPageResponse>builder()
                .result(productService.getProductsByNamePaged(productName, page, size))
                .build();
    }
}
