package com.example.AuctionSite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    Optional<Bid> findTopByAuctionOrderByBidMountDesc(Auction auction);

    List<Bid> findAllByAuctionId(Integer auctionId);

    List<Bid> findAllByUserId(String userId);

    List<Bid> findByAuctionIdAndUserId(Integer auctionId, String userId);
    
    @Query("SELECT b FROM Bid b WHERE b.auction.id = :auctionId ORDER BY b.bidMount DESC")
    List<Bid> findBidsByAuctionIdOrderByBidMountDesc(@Param("auctionId") Integer auctionId);
}
