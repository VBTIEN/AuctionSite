package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.DeliveryType;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, String> {}