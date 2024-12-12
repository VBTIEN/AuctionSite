package com.example.AuctionSite.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.ImageRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.ImageResponse;
import com.example.AuctionSite.service.ImageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImageController {
    ImageService imageService;

    @PostMapping("/upload_images")
    ApiResponse<List<ImageResponse>> uploadImages(@ModelAttribute ImageRequest imageRequest) throws IOException {
        return ApiResponse.<List<ImageResponse>>builder()
                .result(imageService.uploadImages(imageRequest))
                .build();
    }

    @GetMapping("/get_all_images")
    ApiResponse<List<ImageResponse>> getAllImages() {
        return ApiResponse.<List<ImageResponse>>builder()
                .result(imageService.getAllImages())
                .build();
    }

    @GetMapping("/get_image_by_id/{id}")
    ApiResponse<ImageResponse> getImageById(@PathVariable("id") Integer id) {
        return ApiResponse.<ImageResponse>builder()
                .result(imageService.getImageById(id))
                .build();
    }

    @PutMapping("/update_image/{id}")
    ApiResponse<ImageResponse> updateImage(@PathVariable("id") Integer id, @ModelAttribute ImageRequest imageRequest)
            throws IOException {
        return ApiResponse.<ImageResponse>builder()
                .result(imageService.updateImage(id, imageRequest))
                .build();
    }

    @DeleteMapping("/delete_image/{id}")
    ApiResponse<String> deleteImage(@PathVariable("id") Integer id) throws IOException {
        imageService.deleteImage(id);
        return ApiResponse.<String>builder().result("Image deleted").build();
    }
}
