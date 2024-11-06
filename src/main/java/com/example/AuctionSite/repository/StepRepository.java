package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, String> {}
