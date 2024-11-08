package com.example.AuctionSite.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AuctionSite.dto.request.AuctionRequest;
import com.example.AuctionSite.dto.response.AuctionPageResponse;
import com.example.AuctionSite.dto.response.AuctionResponse;
import com.example.AuctionSite.entity.*;
import com.example.AuctionSite.exception.AppException;
import com.example.AuctionSite.exception.ErrorCode;
import com.example.AuctionSite.mapper.AuctionMapper;
import com.example.AuctionSite.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import me.xdrop.fuzzywuzzy.FuzzySearch;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuctionService {
    AuctionRepository auctionRepository;
    AuctionMapper auctionMapper;
    TimeRepository timeRepository;
    ProductRepository productRepository;
    CostRepository costRepository;
    StepRepository stepRepository;
    StatusRepository statusRepository;
    FollowRepository followRepository;
    UserService userService;
    UserRepository userRepository;
    JdbcTemplate jdbcTemplate;

    @PreAuthorize("hasAuthority('CREATE_AUCTION')")
    public AuctionResponse createAuction(AuctionRequest auctionRequest) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Product product = productRepository
                .findById(auctionRequest.getProduct())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean isProductOwnedByUser = user.getProducts().contains(product);
        if (!isProductOwnedByUser) {
            throw new AppException(ErrorCode.PRODUCT_NOT_OF_USER);
        }

        Auction auction = auctionMapper.toAuction(auctionRequest);

        Time time = timeRepository
                .findById(auctionRequest.getTime())
                .orElseThrow(() -> new RuntimeException("Time not found"));

        product.setStatus(statusRepository
                .findById("PENDING_AUCTION")
                .orElseThrow(() -> new RuntimeException("Status not found")));

        Cost cost = costRepository
                .findById(auctionRequest.getCost())
                .orElseThrow(() -> new RuntimeException("Cost not found"));
        Step step = stepRepository
                .findById(auctionRequest.getStep())
                .orElseThrow(() -> new RuntimeException("Step not found"));
        Status status =
                statusRepository.findById("PENDING").orElseThrow(() -> new RuntimeException("Status not found"));

        auction.setTime(time);
        auction.setProduct(product);
        auction.setCost(cost);
        auction.setStep(step);
        auction.setStatus(status);
        auctionRepository.save(auction);

        userService.addAuctionToUser(userId, auction.getId());

        return auctionMapper.toAuctionResponse(auction);
    }

    @PreAuthorize("hasAuthority('GET_ALL_AUCTIONS')")
    public List<AuctionResponse> getAllProducts() {
        return auctionRepository.findAll().stream()
                .map(auctionMapper::toAuctionResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_AUCTION_BY_ID')")
    public AuctionResponse getAuctionById(Integer id) {
        Auction auction = auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not found"));
        return auctionMapper.toAuctionResponse(auction);
    }

    @PreAuthorize("hasAuthority('GET_AUCTION_BY_NAME')")
    public List<AuctionResponse> getAuctionByName(String name) {
        List<Auction> auctions = auctionRepository.findAllByName(name);
        return auctions.stream().map(auctionMapper::toAuctionResponse).toList();
    }

    @PreAuthorize("hasAuthority('UPDATE_AUCTION')")
    public AuctionResponse updateAuction(Integer id, AuctionRequest auctionRequest) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        boolean isAdmin =
                user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

        Auction auction;
        if (isAdmin) {
            auction = auctionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_FOUND));
        } else {
            auction = user.getAuctions().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_OF_USER));
        }

        // Auction auction = auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not
        // found"));
        auctionMapper.toUpdateAuction(auction, auctionRequest);
        Time time = timeRepository
                .findById(auctionRequest.getTime())
                .orElseThrow(() -> new RuntimeException("Time not found"));

        Product product = productRepository
                .findById(auctionRequest.getProduct())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStatus(statusRepository
                .findById("PENDING_AUCTION")
                .orElseThrow(() -> new RuntimeException("Status not found")));

        Cost cost = costRepository
                .findById(auctionRequest.getCost())
                .orElseThrow(() -> new RuntimeException("Cost not found"));
        Step step = stepRepository
                .findById(auctionRequest.getStep())
                .orElseThrow(() -> new RuntimeException("Step not found"));
        Status status =
                statusRepository.findById("PENDING").orElseThrow(() -> new RuntimeException("Status not found"));

        auction.setTime(time);
        auction.setProduct(product);
        auction.setCost(cost);
        auction.setStep(step);
        auction.setStatus(status);
        return auctionMapper.toAuctionResponse(auctionRepository.save(auction));
    }

    @PreAuthorize("hasAuthority('DELETE_AUCTION')")
    public void deleteAuction(Integer id) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        boolean isAdmin =
                user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("ADMIN"));

        Auction auction;
        if (isAdmin) {
            auction = auctionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_FOUND));
        } else {
            auction = user.getAuctions().stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_OF_USER));
        }

        List<Follow> follows = followRepository.findByAuctionId(id);
        followRepository.deleteAll(follows);

        Product product = productRepository
                .findById(auction.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setStatus(
                statusRepository.findById("SUSPENDED").orElseThrow(() -> new RuntimeException("Status not found")));

        String deleteUserAuctionQuery = "DELETE FROM user_auctions WHERE auctions_id = ?";
        jdbcTemplate.update(deleteUserAuctionQuery, id);

        auctionRepository.deleteById(id);
    }

    public List<AuctionResponse> searchAuctionsByName(String name, int threshold) {
        List<Auction> auctions = auctionRepository.findAll();
        List<Auction> filteredAuctions = auctions.stream()
                .filter(auction -> FuzzySearch.ratio(auction.getName(), name) >= threshold)
                .toList();

        return filteredAuctions.stream().map(auctionMapper::toAuctionResponse).collect(Collectors.toList());
    }

    public List<AuctionResponse> getAuctionsPending() {
        Status status = statusRepository.findById("PENDING").orElseThrow();
        List<Auction> auctions = auctionRepository.findAllByStatus(status);
        return auctions.stream().map(auctionMapper::toAuctionResponse).toList();
    }

    public List<AuctionResponse> getAuctionsOngoing() {
        Status status = statusRepository.findById("ONGOING").orElseThrow();
        List<Auction> auctions = auctionRepository.findAllByStatus(status);
        return auctions.stream().map(auctionMapper::toAuctionResponse).toList();
    }

    public List<AuctionResponse> getAuctionsEnded() {
        Status status = statusRepository.findById("ENDED").orElseThrow();
        List<Auction> auctions = auctionRepository.findAllByStatus(status);
        return auctions.stream().map(auctionMapper::toAuctionResponse).toList();
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateAuctionStatus() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime adjustedNow = now.plusMinutes(1);

        Status ongoingStatus = statusRepository
                .findById("ONGOING")
                .orElseThrow(() -> new RuntimeException("Status ONGOING not found"));
        Status pendingStatus = statusRepository
                .findById("PENDING")
                .orElseThrow(() -> new RuntimeException("Status PENDING not found"));

        List<Auction> pendingAuctions = auctionRepository.findAllByStatusAndStartTimeBefore(pendingStatus, adjustedNow);
        for (Auction auction : pendingAuctions) {
            auction.setStatus(ongoingStatus);
            log.info("Auction {} is now ONGOING", auction.getId());
        }

        Status endedStatus =
                statusRepository.findById("ENDED").orElseThrow(() -> new RuntimeException("Status ENDED not found"));

        List<Auction> ongoingAuctions = auctionRepository.findAllByStatus(ongoingStatus);
        for (Auction auction : ongoingAuctions) {
            Duration auctionDuration = auction.getTime().getTime();
            LocalDateTime auctionEndTime = auction.getStartTime().plus(auctionDuration);

            if (auctionEndTime.isBefore(adjustedNow)) {
                auction.setStatus(endedStatus);
                log.info("Auction {} has ENDED", auction.getId());
            }
        }

        auctionRepository.saveAll(pendingAuctions);
        auctionRepository.saveAll(ongoingAuctions);
    }

    public AuctionPageResponse getAllAuctionsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Auction> auctionPage = auctionRepository.findAll(pageable);

        List<AuctionResponse> auctions = auctionPage.getContent().stream()
                .map(auctionMapper::toAuctionResponse)
                .collect(Collectors.toList());

        return AuctionPageResponse.builder()
                .auctions(auctions)
                .totalPages(auctionPage.getTotalPages())
                .totalElements(auctionPage.getTotalElements())
                .build();
    }

    public AuctionPageResponse getAllAuctionsPendingPaged(int page, int size) {
        Status status = statusRepository
                .findById("PENDING")
                .orElseThrow(() -> new RuntimeException("Status PENDING not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Auction> auctionPage = auctionRepository.findAllByStatus(status, pageable);

        List<AuctionResponse> auctions = auctionPage.getContent().stream()
                .map(auctionMapper::toAuctionResponse)
                .toList();

        return AuctionPageResponse.builder()
                .auctions(auctions)
                .totalPages(auctionPage.getTotalPages())
                .totalElements(auctionPage.getTotalElements())
                .build();
    }

    public AuctionPageResponse getAllAuctionsOngoingPaged(int page, int size) {
        Status status = statusRepository
                .findById("ONGOING")
                .orElseThrow(() -> new RuntimeException("Status ONGOING not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Auction> auctionPage = auctionRepository.findAllByStatus(status, pageable);

        List<AuctionResponse> auctions = auctionPage.getContent().stream()
                .map(auctionMapper::toAuctionResponse)
                .toList();

        return AuctionPageResponse.builder()
                .auctions(auctions)
                .totalPages(auctionPage.getTotalPages())
                .totalElements(auctionPage.getTotalElements())
                .build();
    }

    public AuctionPageResponse getAllAuctionsEndedPaged(int page, int size) {
        Status status =
                statusRepository.findById("ENDED").orElseThrow(() -> new RuntimeException("Status ENDED not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Auction> auctionPage = auctionRepository.findAllByStatus(status, pageable);

        List<AuctionResponse> auctions = auctionPage.getContent().stream()
                .map(auctionMapper::toAuctionResponse)
                .toList();

        return AuctionPageResponse.builder()
                .auctions(auctions)
                .totalPages(auctionPage.getTotalPages())
                .totalElements(auctionPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_AUCTIONS_BY_USERID')")
    public List<AuctionResponse> getAllAuctionsByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Auction> auctions = user.getAuctions();
        return auctions.stream().map(auctionMapper::toAuctionResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_ALL_AUCTIONS_OF_USER')")
    public List<AuctionResponse> getAllAuctionsOfUser() {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Auction> auctions = user.getAuctions();
        return auctions.stream().map(auctionMapper::toAuctionResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_AUCTION_BY_ID_OF_USER')")
    public AuctionResponse getAuctionByIdOfUser(Integer id) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Auction auction = user.getAuctions().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.AUCTION_NOT_OF_USER));
        return auctionMapper.toAuctionResponse(auction);
    }

    @PreAuthorize("hasAuthority('GET_AUCTION_BY_NAME_OF_USER')")
    public List<AuctionResponse> getAuctionByNameOfUser(String name) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        List<AuctionResponse> auctions = user.getAuctions().stream()
                .filter(auction -> auction.getName().equalsIgnoreCase(name))
                .map(auctionMapper::toAuctionResponse)
                .toList();

        if (auctions.isEmpty()) {
            throw new AppException(ErrorCode.AUCTION_NOT_OF_USER);
        }
        return auctions;
    }
}
