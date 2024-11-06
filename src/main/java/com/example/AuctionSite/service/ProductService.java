package com.example.AuctionSite.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.AuctionSite.dto.request.ProductRequest;
import com.example.AuctionSite.dto.response.ProductPageResponse;
import com.example.AuctionSite.dto.response.ProductResponse;
import com.example.AuctionSite.entity.Image;
import com.example.AuctionSite.entity.Product;
import com.example.AuctionSite.entity.Status;
import com.example.AuctionSite.mapper.ProductMapper;
import com.example.AuctionSite.repository.CategoryRepository;
import com.example.AuctionSite.repository.ImageRepository;
import com.example.AuctionSite.repository.ProductRepository;
import com.example.AuctionSite.repository.StatusRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import me.xdrop.fuzzywuzzy.FuzzySearch;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;
    ImageService imageService;
    ImageRepository imageRepository;
    StatusRepository statusRepository;
    UserService userService;
    JdbcTemplate jdbcTemplate;

    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public ProductResponse createProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
        Product product = productMapper.toProduct(productRequest);

        Status status = statusRepository.findById("ADDED").orElseThrow();
        product.setStatus(status);

        var categories = categoryRepository.findAllById(productRequest.getCategories());
        product.setCategories(new HashSet<>(categories));

        Set<Image> images = new HashSet<>();
        for (MultipartFile file : productRequest.getImages()) {
            Image image = imageService.saveImage(file);
            images.add(image);
        }

        product.setImages(images);
        product = productRepository.save(product);

        String userId = userService.getUserId();
        userService.addProductToUser(userId, product.getId());

        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS')")
    public List<ProductResponse> getAllProducts() {
        var product = productRepository.findAll();
        return product.stream().map(productMapper::toProductResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_PRODUCT_BY_ID')")
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasAuthority('GET_PRODUCT_BY_NAME')")
    public List<ProductResponse> getProductByName(String name) {
        List<Product> products = productRepository.findAllByName(name);
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    public ProductResponse updateProduct(Integer id, ProductRequest productRequest) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        productMapper.toUpdateProduct(product, productRequest);

        var categories = categoryRepository.findAllById(productRequest.getCategories());
        product.setCategories(new HashSet<>(categories));

        List<MultipartFile> files = productRequest.getImages();
        if (files != null && !files.isEmpty()) {
            Set<String> existingImageUrls =
                    product.getImages().stream().map(Image::getImageURL).collect(Collectors.toSet());

            for (MultipartFile newFile : files) {
                if (newFile.isEmpty()) continue;

                String newImageUrl = "/images/" + newFile.getOriginalFilename();
                if (existingImageUrls.contains(newImageUrl)) {
                    continue;
                }

                Image newImage = imageService.saveImage(newFile);
                product.getImages().add(newImage);
            }
        }
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @Transactional
    public void deleteProduct(Integer id) throws IOException {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        List<String> allImageUrls =
                imageRepository.findAll().stream().map(Image::getImageURL).toList();

        for (Image image : new HashSet<>(product.getImages())) {
            String imageUrl = image.getImageURL();

            boolean isDuplicate =
                    allImageUrls.stream().filter(url -> url.equals(imageUrl)).count() > 1;

            if (isDuplicate) {
                imageRepository.deleteById(image.getId());
            } else {
                imageService.deleteImage(image.getId());
            }
        }

        String deleteUserProductQuery = "DELETE FROM user_products WHERE products_id = ?";
        jdbcTemplate.update(deleteUserProductQuery, id);

        productRepository.delete(product);
    }

    public List<ProductResponse> searchProductsByName(String name, int threshold) {
        List<Product> products = productRepository.findAll();
        List<Product> filteredProducts = products.stream()
                .filter(product -> FuzzySearch.ratio(product.getName(), name) >= threshold)
                .toList();

        return filteredProducts.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsPendingAuction() {
        Status status = statusRepository.findById("PENDING_AUCTION").orElseThrow();
        List<Product> products = productRepository.findAllByStatus(status);
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    public List<ProductResponse> getProductsActive() {
        Status status = statusRepository.findById("ACTIVE").orElseThrow();
        List<Product> products = productRepository.findAllByStatus(status);
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    public ProductPageResponse getAllProductsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> products = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());

        return ProductPageResponse.builder()
                .products(products)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    public ProductPageResponse getAllProductsPendingAuctionPaged(int page, int size) {
        Status status = statusRepository
                .findById("PENDING_AUCTION")
                .orElseThrow(() -> new RuntimeException("Status PENDING_AUCTION not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAllByStatus(status, pageable);

        List<ProductResponse> products = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(products)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    public ProductPageResponse getAllProductsActivePaged(int page, int size) {
        Status status =
                statusRepository.findById("ACTIVE").orElseThrow(() -> new RuntimeException("Status ACTIVE not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAllByStatus(status, pageable);

        List<ProductResponse> products = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(products)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    public ProductPageResponse getAllProductsSoldPaged(int page, int size) {
        Status status =
                statusRepository.findById("SOLD").orElseThrow(() -> new RuntimeException("Status SOLD not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAllByStatus(status, pageable);

        List<ProductResponse> products = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(products)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }
}
