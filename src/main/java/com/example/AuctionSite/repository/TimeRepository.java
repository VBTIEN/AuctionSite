package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TimeRepository extends JpaRepository<Time, String> {
}
