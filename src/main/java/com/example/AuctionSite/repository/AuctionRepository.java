package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    List<Auction> findAllByName(String name);
    
    List<Auction> findByStatusAndStartTimeBefore(Status status, LocalDateTime currentTime);
}
