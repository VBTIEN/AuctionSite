package com.example.AuctionSite.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.example.AuctionSite.entity.User;
import com.example.AuctionSite.exception.AppException;
import com.example.AuctionSite.exception.ErrorCode;
import com.example.AuctionSite.mapper.ProductMapper;
import com.example.AuctionSite.repository.*;

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
    UserRepository userRepository;

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
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        if (id == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }

        boolean isAdmin =
                user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

        Product product;
        if (isAdmin) {
            product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        } else {
            product = user.getProducts().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_OF_USER));
        }

        productMapper.toUpdateProduct(product, productRequest);

        var categories = categoryRepository.findAllById(productRequest.getCategories());
        product.setCategories(new HashSet<>(categories));

        List<MultipartFile> files = productRequest.getImages();
        if (files != null && !files.isEmpty()) {
            Set<String> existingImageUrls =
                    product.getImages().stream().map(Image::getImageURL).collect(Collectors.toSet());

            for (MultipartFile newFile : files) {
                if (newFile.isEmpty()) continue;

                String newImageUrl = "/auctionsite/images/" + newFile.getOriginalFilename();
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
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        boolean isAdmin =
                user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

        Product product;
        if (isAdmin) {
            product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        } else {
            product = user.getProducts().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_OF_USER));
        }

        if (!"ADDED".equalsIgnoreCase(product.getStatus().getName())) {
            throw new AppException(ErrorCode.PRODUCT_NOT_ALLOWED_TO_DELETE);
        }

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

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS_BY_USERID')")
    public List<ProductResponse> getAllProductsByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Product> products = user.getProducts();
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS_OF_USER')")
    public List<ProductResponse> getAllProductsOfUser() {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Product> products = user.getProducts();
        return products.stream().map(productMapper::toProductResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_PRODUCT_BY_ID_OF_USER')")
    public ProductResponse getProductByIdOfUser(Integer id) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Product product = user.getProducts().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_OF_USER));
        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasAuthority('GET_PRODUCT_BY_NAME_OF_USER')")
    public List<ProductResponse> getProductByNameOfUser(String name) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        List<ProductResponse> products = user.getProducts().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .map(productMapper::toProductResponse)
                .toList();

        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_OF_USER);
        }
        return products;
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS_PENDING_AUCTION_PAGED_OF_USER')")
    public ProductPageResponse getAllProductsPendingAuctionPagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Product> products = user.getProducts();

        Status status = statusRepository
                .findById("PENDING_AUCTION")
                .orElseThrow(() -> new RuntimeException("Status PENDING_AUCTION not found"));

        List<Product> filteredProducts = products.stream()
                .filter(product -> status.equals(product.getStatus()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filteredProducts.size());
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        Page<Product> productPage =
                new PageImpl<>(filteredProducts.subList(start, end), pageable, filteredProducts.size());

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(productResponses)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS_ACTIVE_PAGED_OF_USER')")
    public ProductPageResponse getAllProductsActivePagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Product> products = user.getProducts();

        Status status =
                statusRepository.findById("ACTIVE").orElseThrow(() -> new RuntimeException("Status ACTIVE not found"));

        List<Product> filteredProducts = products.stream()
                .filter(product -> status.equals(product.getStatus()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filteredProducts.size());
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        Page<Product> productPage =
                new PageImpl<>(filteredProducts.subList(start, end), pageable, filteredProducts.size());

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(productResponses)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS_ADDED_PAGED_OF_USER')")
    public ProductPageResponse getAllProductsAddedPagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Product> products = user.getProducts();

        Status status =
                statusRepository.findById("ADDED").orElseThrow(() -> new RuntimeException("Status ADDED not found"));

        List<Product> filteredProducts = products.stream()
                .filter(product -> status.equals(product.getStatus()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filteredProducts.size());
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        Page<Product> productPage =
                new PageImpl<>(filteredProducts.subList(start, end), pageable, filteredProducts.size());

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(productResponses)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS_SOLD_PAGED_OF_USER')")
    public ProductPageResponse getAllProductsSoldPagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Product> products = user.getProducts();

        Status status =
                statusRepository.findById("SOLD").orElseThrow(() -> new RuntimeException("Status SOLD not found"));

        List<Product> filteredProducts = products.stream()
                .filter(product -> status.equals(product.getStatus()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filteredProducts.size());
        int end = Math.min((start + pageable.getPageSize()), filteredProducts.size());
        Page<Product> productPage =
                new PageImpl<>(filteredProducts.subList(start, end), pageable, filteredProducts.size());

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(productResponses)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_PRODUCTS_SA_PAGED_OF_USER')")
    public ProductPageResponse getAllProductsSAPagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Set<Product> productsSA = user.getProductSuccessfullyAuctioned();

        Pageable pageable = PageRequest.of(page, size);
        List<Product> productList = new ArrayList<>(productsSA);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), productList.size());

        if (start > productList.size()) {
            return ProductPageResponse.builder()
                    .products(Collections.emptyList())
                    .totalPages(0)
                    .totalElements(0L)
                    .build();
        }

        Page<Product> productPage = new PageImpl<>(productList.subList(start, end), pageable, productList.size());

        List<ProductResponse> products = productPage.getContent().stream()
                .map(productMapper::toProductResponse)
                .toList();

        return ProductPageResponse.builder()
                .products(products)
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    public ProductPageResponse getProductsByNamePaged(String productName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(productName, pageable);

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
