package com.example.AuctionSite.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageRequest {
    @NotBlank(message = "FILE_BLANK")
    List<MultipartFile> files;
}
