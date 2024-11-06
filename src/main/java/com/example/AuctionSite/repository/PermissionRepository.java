package com.example.AuctionSite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
