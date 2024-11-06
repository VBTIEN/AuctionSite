package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {}
