package com.example.AuctionSite.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Status;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    List<Auction> findAllByName(String name);

    List<Auction> findAllByStatus(Status status);

    List<Auction> findAllByStatusAndStartTimeBefore(Status status, LocalDateTime dateTime);

    Page<Auction> findAllByStatus(Status status, Pageable pageable);

    List<Auction> findByStatusName(String statusName);
    
    @Query("""
    SELECT u.id\s
    FROM User u\s
    JOIN u.auctions a\s
    WHERE a.id = :auctionId
""")
    Optional<String> findCreatorIdByAuctionId(@Param("auctionId") Integer auctionId);
}
