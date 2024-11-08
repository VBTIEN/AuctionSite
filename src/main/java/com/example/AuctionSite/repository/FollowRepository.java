package com.example.AuctionSite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Follow;
import com.example.AuctionSite.entity.User;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByAuctionId(Integer auctionId);

    Optional<Follow> findByAuctionIdAndUserId(Integer auctionId, String userId);

    List<Follow> findAllByUser(User user);

    List<Follow> findAllByAuction(Auction auction);
}
