package com.example.AuctionSite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Override
    Optional<Role> findById(String id);
}
