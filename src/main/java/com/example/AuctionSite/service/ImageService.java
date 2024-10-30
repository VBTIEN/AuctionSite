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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ImageService {
    ImageRepository imageRepository;
    ImageMapper imageMapper;
    
    String UPLOAD_DIR = "src/main/resources/static/images/";
    
    @PreAuthorize("hasAuthority('UPLOAD_IMAGES')")
    public List<ImageResponse> uploadImages(ImageRequest imageRequest) throws IOException {
        List<ImageResponse> imageResponses = new ArrayList<>();
        
        for (MultipartFile file : imageRequest.getFiles()) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }
            
            String fileName = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            Path path = Paths.get(filePath);
            
            Files.createDirectories(path.getParent());
            
            if (imageRepository.existsByImageURL("/images/" + fileName)) {
                throw new RuntimeException("Image existed: " + fileName);
            }
            
            Files.write(path, file.getBytes());
            
            Image image = Image.builder()
                .imageURL("/images/" + fileName)
                .build();
            imageResponses.add(imageMapper.toImageResponse(imageRepository.save(image)));
        }
        return imageResponses;
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
        List<MultipartFile> files = imageRequest.getFiles();
        if (files.size() != 1) {
            throw new RuntimeException("Only one image can be uploaded at a time");
        }
        
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        
        MultipartFile newFile = files.getFirst();
        String newFileName=newFile.getOriginalFilename();
        String newFilePath = UPLOAD_DIR + newFileName;
        Path path = Paths.get(newFilePath);
        
        Files.createDirectories(path.getParent());
        
        if (imageRepository.existsByImageURL("/images/" + newFileName)) {
            throw new RuntimeException("Image existed: " + newFileName);
        }
        
        String oldFilePath = "src/main/resources/static" + image.getImageURL();
        Files.deleteIfExists(Paths.get(oldFilePath));
        
        Files.write(path, newFile.getBytes());
        
        image.setImageURL("/images/" + newFile.getOriginalFilename());
        return imageMapper.toImageResponse(imageRepository.save(image));
    }
    
    @PreAuthorize("hasAuthority('DELETE_IMAGE')")
    public void deleteImage(Integer id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        String filePath = "src/main/resources/static" + image.getImageURL();
        Files.deleteIfExists(Paths.get(filePath));
        imageRepository.deleteById(id);
    }
}