package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.Ranks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RanksRepository extends JpaRepository<Ranks, String> {
}
