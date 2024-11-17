package com.example.AuctionSite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    Optional<Receipt> findByProductId(Integer productId);

    List<Receipt> findAllBySellerId(String sellerId);

    List<Receipt> findAllByBuyerId(String buyerId);
}
