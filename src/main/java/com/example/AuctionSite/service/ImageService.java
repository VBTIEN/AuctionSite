package com.example.AuctionSite.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.AuctionSite.dto.request.ImageRequest;
import com.example.AuctionSite.dto.response.ImageResponse;
import com.example.AuctionSite.entity.Image;
import com.example.AuctionSite.mapper.ImageMapper;
import com.example.AuctionSite.repository.ImageRepository;
import com.example.AuctionSite.repository.ProductRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {
    ImageRepository imageRepository;
    ImageMapper imageMapper;
    ProductRepository productRepository;

    String UPLOAD_DIR = "src/main/resources/static/images/";

    @PreAuthorize("hasAuthority('UPLOAD_IMAGES')")
    public List<ImageResponse> uploadImages(ImageRequest imageRequest) throws IOException {
        List<ImageResponse> imageResponses = new ArrayList<>();

        for (MultipartFile file : imageRequest.getFiles()) {
            if (file.isEmpty()) {
                continue;
            }

            String fileName = file.getOriginalFilename();
            String imageUrl = "/images/" + fileName;

            if (imageRepository.existsByImageURL("/images/" + fileName)) {
                throw new RuntimeException("Image existed: " + fileName);
            }

            Image savedImage = saveImage(file);

            ImageResponse imageResponse = imageMapper.toImageResponse(savedImage);
            imageResponses.add(imageResponse);
        }
        return imageResponses;
    }

    public Image saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String fileName = file.getOriginalFilename();
        String filePath = UPLOAD_DIR + fileName;
        Path path = Paths.get(filePath);

        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        Image image = Image.builder().imageURL("/images/" + fileName).build();
        imageRepository.save(image);
        return image;
    }

    @PreAuthorize("hasAuthority('GET_ALL_IMAGES')")
    public List<ImageResponse> getAllImages() {
        return imageRepository.findAll().stream()
                .map(imageMapper::toImageResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_IMAGE_BY_ID')")
    public ImageResponse getImageById(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        return imageMapper.toImageResponse(image);
    }

    @PreAuthorize("hasAuthority('UPDATE_IMAGE')")
    public ImageResponse updateImage(Integer id, ImageRequest imageRequest) throws IOException {
        List<MultipartFile> files = imageRequest.getFiles();

        if (files.size() != 1) {
            throw new RuntimeException("Only one image can be uploaded at a time");
        }

        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));

        MultipartFile newFile = files.getFirst();
        updateImageDetails(image, newFile);

        return imageMapper.toImageResponse(imageRepository.save(image));
    }

    public void updateImageDetails(Image image, MultipartFile newFile) throws IOException {
        String newFileName = newFile.getOriginalFilename();
        String newFilePath = UPLOAD_DIR + newFileName;
        Path path = Paths.get(newFilePath);

        Files.createDirectories(path.getParent());

        if (imageRepository.existsByImageURL("/images/" + newFileName)) {
            throw new RuntimeException("Image existed: " + newFileName);
        }

        String oldFilePath = "src/main/resources/static" + image.getImageURL();
        Files.deleteIfExists(Paths.get(oldFilePath));

        Files.write(path, newFile.getBytes());

        image.setImageURL("/images/" + newFileName);
    }

    @PreAuthorize("hasAuthority('DELETE_IMAGE')")
    public void deleteImage(Integer id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        String filePath = "src/main/resources/static" + image.getImageURL();
        Files.deleteIfExists(Paths.get(filePath));
        imageRepository.deleteById(id);
    }
}
