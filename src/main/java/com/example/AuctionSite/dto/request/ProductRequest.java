package com.example.AuctionSite.dto.request;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "PRODUCTNAME_BLANK")
    @Size(max = 50, message = "PRODUCTNAME_INVALID_SIZE")
    String name;

    @NotBlank(message = "PRODUCT_DESCRIPTION_BLANK")
    String description;

    @NotBlank(message = "PRODUCT_CATEGORIES_BLANK")
    Set<String> categories;

    List<MultipartFile> images;
}
