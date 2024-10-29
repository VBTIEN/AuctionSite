package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<Cost,String> {

}
