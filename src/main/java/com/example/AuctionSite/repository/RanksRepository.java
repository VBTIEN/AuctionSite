package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Ranks;

@Repository
public interface RanksRepository extends JpaRepository<Ranks, String> {}
