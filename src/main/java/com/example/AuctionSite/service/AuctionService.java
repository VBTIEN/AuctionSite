package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.AuctionRequest;
import com.example.AuctionSite.dto.response.AuctionResponse;
import com.example.AuctionSite.entity.*;
import com.example.AuctionSite.mapper.AuctionMapper;
import com.example.AuctionSite.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuctionService {
    AuctionRepository auctionRepository;
    AuctionMapper auctionMapper;
    TimeRepository timeRepository;
    ProductRepository productRepository;
    CostRepository costRepository;
    StepRepository stepRepository;
    
    public AuctionResponse createAuction(AuctionRequest auctionRequest) {
        Auction auction = auctionMapper.toAuction(auctionRequest);
        Time time = timeRepository.findById(auctionRequest.getTime()).orElseThrow(() -> new RuntimeException("Time not found"));
        Product product = productRepository.findByName(auctionRequest.getProduct()).orElseThrow(() -> new RuntimeException("Product not found"));
        Cost cost = costRepository.findById(auctionRequest.getCost()).orElseThrow(() -> new RuntimeException("Cost not found"));
        Step step = stepRepository.findById(auctionRequest.getStep()).orElseThrow(() -> new RuntimeException("Step not found"));
        auction.setTime(time);
        auction.setProduct(product);
        auction.setCost(cost);
        auction.setStep(step);
        return auctionMapper.toAuctionResponse(auctionRepository.save(auction));
    }
    
    public List<AuctionResponse> getAllProducts() {
        return auctionRepository.findAll().stream().map(auctionMapper::toAuctionResponse).toList();
    }
    
    public AuctionResponse getAuctionById(Integer id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not found"));
        return auctionMapper.toAuctionResponse(auction);
    }
    
    public AuctionResponse getAuctionByName(String name) {
        Auction auction = auctionRepository.findByName(name).orElseThrow(() -> new RuntimeException("Auction not found"));
        return auctionMapper.toAuctionResponse(auction);
    }
    
    public AuctionResponse updateAuction(Integer id, AuctionRequest auctionRequest) {
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not found"));
        auctionMapper.toUpdateAuction(auction, auctionRequest);
        Time time = timeRepository.findById(auctionRequest.getTime()).orElseThrow(() -> new RuntimeException("Time not found"));
        Product product = productRepository.findByName(auctionRequest.getProduct()).orElseThrow(() -> new RuntimeException("Product not found"));
        Cost cost = costRepository.findById(auctionRequest.getCost()).orElseThrow(() -> new RuntimeException("Cost not found"));
        Step step = stepRepository.findById(auctionRequest.getStep()).orElseThrow(() -> new RuntimeException("Step not found"));
        auction.setTime(time);
        auction.setProduct(product);
        auction.setCost(cost);
        auction.setStep(step);
        return auctionMapper.toAuctionResponse(auctionRepository.save(auction));
    }
    
    public void deleteAuction(Integer id) {
        auctionRepository.deleteById(id);
    }
}
