package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.PaymentType;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, String> {}
