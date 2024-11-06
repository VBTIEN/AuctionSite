package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time, String> {}
