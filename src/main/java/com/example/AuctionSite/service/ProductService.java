package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.ProductRequest;
import com.example.AuctionSite.dto.response.ProductResponse;
import com.example.AuctionSite.entity.Product;
import com.example.AuctionSite.mapper.ProductMapper;
import com.example.AuctionSite.repository.CategoryRepository;
import com.example.AuctionSite.repository.ImageRepository;
import com.example.AuctionSite.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;
    ImageRepository imageRepository;
    
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        var categories = categoryRepository.findAllById(productRequest.getCategories());
        product.setCategories(new HashSet<>(categories));
        /*var images = imageRepository.findAllById(productRequest.getImages());
        product.setImages(new HashSet<>(images));*/
        return productMapper.toProductResponse(productRepository.save(product));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_PRODUCT')")
    public List<ProductResponse> getAllProducts() {
        var product = productRepository.findAll();
        return product.stream().map(productMapper::toProductResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    public ProductResponse updateProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.toUpdateProduct(product, productRequest);
        var categories = categoryRepository.findAllById(productRequest.getCategories());
        product.setCategories(new HashSet<>(categories));
        /*var images = imageRepository.findAllById(productRequest.getImages());
        product.setImages(new HashSet<>(images));*/
        return productMapper.toProductResponse(productRepository.save(product));
    }
    
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
