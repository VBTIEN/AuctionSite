package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, String> {
    Optional<Auction> findByName(String name);
}
