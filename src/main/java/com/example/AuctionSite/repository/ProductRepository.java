package com.example.AuctionSite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Product;
import com.example.AuctionSite.entity.Status;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);

    List<Product> findAllByName(String name);

    List<Product> findAllByStatus(Status status);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByStatus(Status status, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
