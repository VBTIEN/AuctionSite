package com.example.AuctionSite.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.AuctionSite.entity.Status;
import com.example.AuctionSite.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.auctions a WHERE a.id = :auctionId")
    Optional<User> findCreatorByAuctionId(@Param("auctionId") Integer auctionId);

    Set<User> findByStatus(Status status);
}
