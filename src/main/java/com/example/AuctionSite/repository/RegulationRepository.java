package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Regulation;

@Repository
public interface RegulationRepository extends JpaRepository<Regulation, String> {}
