package com.example.AuctionSite.repository;

import com.example.AuctionSite.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {
    boolean existsByImageURL(String imageURL);
}
