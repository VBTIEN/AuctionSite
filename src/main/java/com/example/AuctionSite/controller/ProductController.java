package com.example.AuctionSite.controller;

import com.example.AuctionSite.dto.request.ProductRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.ProductResponse;
import com.example.AuctionSite.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductController {
    ProductService productService;
    
    @PostMapping(value = "/create_product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<ProductResponse> createProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
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
    ApiResponse<ProductResponse> updateProduct(@PathVariable("id") Integer id, @ModelAttribute ProductRequest productRequest) throws IOException {
        return ApiResponse.<ProductResponse>builder()
            .result(productService.updateProduct(id, productRequest))
            .build();
    }
    
    @DeleteMapping("/delete_product/{id}")
    ApiResponse<String> deleteProduct(@PathVariable("id") Integer id) throws IOException {
        productService.deleteProduct(id);
        return ApiResponse.<String>builder()
            .result("Product deleted")
            .build();
    }
}
