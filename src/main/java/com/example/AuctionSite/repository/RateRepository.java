package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate,Float> {
}
