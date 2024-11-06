package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Benefit;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, String> {}
