package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.ImageRequest;
import com.example.AuctionSite.dto.response.ImageResponse;
import com.example.AuctionSite.entity.Image;
import com.example.AuctionSite.mapper.ImageMapper;
import com.example.AuctionSite.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ImageService {
    ImageRepository imageRepository;
    ImageMapper imageMapper;
    
    String UPLOAD_DIR = "src/main/resources/static/images/";
    
    @PreAuthorize("hasAuthority('UPLOAD_IMAGE')")
    public ImageResponse uploadImage(ImageRequest imageRequest) throws IOException {
        MultipartFile file = imageRequest.getFile();
        
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        
        String filePath = UPLOAD_DIR +file.getOriginalFilename();
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        
        Image image = Image.builder()
            .imageURL("/images/" + file.getOriginalFilename())
            .build();
        return imageMapper.toImageResponse(imageRepository.save(image));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_IMAGES')")
    public List<ImageResponse> getAllImages() {
        return imageRepository.findAll().stream().map(imageMapper::toImageResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('GET_IMAGE_BY_ID')")
    public ImageResponse getImageById(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        return imageMapper.toImageResponse(image);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_IMAGE')")
    public ImageResponse updateImage(Integer id, ImageRequest imageRequest) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        
        String oldFilePath = "src/main/resources/static" + image.getImageURL();
        Files.deleteIfExists(Paths.get(oldFilePath));
        
        MultipartFile newFile = imageRequest.getFile();
        String newFilePath = UPLOAD_DIR +newFile.getOriginalFilename();
        Path path = Paths.get(newFilePath);
        Files.createDirectories(path.getParent());
        Files.write(path, newFile.getBytes());
        
        image.setImageURL("/images/" +newFile.getOriginalFilename());
        return imageMapper.toImageResponse(imageRepository.save(image));
    }
    
    @PreAuthorize("hasAuthority('DELETE_IMAGE')")
    public void deleteImage(Integer id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        String oldFilePath = "src/main/resources/static" + image.getImageURL();
        Files.deleteIfExists(Paths.get(oldFilePath));
        imageRepository.deleteById(id);
    }
}
