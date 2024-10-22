package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.ToAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToAuctionRepository extends JpaRepository<ToAuction, String> {
}
