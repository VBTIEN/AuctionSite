package com.example.AuctionSite.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AuctionSite.dto.request.AuctionRequest;
import com.example.AuctionSite.dto.response.*;
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
    ImageRepository imageRepository;
    ReceiptRepository receiptRepository;
    DeliveryTypeRepository deliveryTypeRepository;
    PaymentTypeRepository paymentTypeRepository;

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
        auction.setDateCreated(LocalDate.now());

        if (auctionRequest.getImageId() != null) {
            Image image = product.getImages().stream()
                    .filter(img -> img.getId().equals(auctionRequest.getImageId()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND_IN_PRODUCT_IMAGES));
            auction.setImage(image);
        } else {
            Image defaultImage = imageRepository.findById(1).orElseThrow();
            auction.setImage(defaultImage);
        }

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

        if (auctionRequest.getImageId() != null) {
            Image image = product.getImages().stream()
                    .filter(img -> img.getId().equals(auctionRequest.getImageId()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.IMAGE_NOT_FOUND_IN_PRODUCT_IMAGES));
            auction.setImage(image);
        } else {
            Image defaultImage = imageRepository.findById(1).orElseThrow();
            auction.setImage(defaultImage);
        }

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
        Status waitingForPaymentStatus = statusRepository
                .findById("WAITING_FOR_PAYMENT")
                .orElseThrow(() -> new RuntimeException("Status WAITING_FOR_PAYMENT not found"));

        List<Auction> ongoingAuctions = auctionRepository.findAllByStatus(ongoingStatus);
        for (Auction auction : ongoingAuctions) {
            Duration auctionDuration = auction.getTime().getTime();
            LocalDateTime auctionEndTime = auction.getStartTime().plus(auctionDuration);

            Product productOfAuction = auction.getProduct();
            productOfAuction.setStatus(statusRepository.findById("ACTIVE").orElseThrow());

            if (auctionEndTime.isBefore(adjustedNow)) {
                auction.setStatus(endedStatus);
                auction.setEndTime(auctionEndTime);
                log.info("Auction {} has ENDED", auction.getId());

                Optional<Bid> highestBid = auction.getBids().stream().max(Comparator.comparing(Bid::getBidMount));

                highestBid.ifPresent(bid -> {
                    auction.setFinalCost(bid.getBidMount().intValue());
                    auction.setWinningBidder(bid.getUser());

                    Receipt receipt = Receipt.builder()
                            .name("Receipt for product " + auction.getProduct().getName())
                            .description("Receipt generated for the auction " + auction.getName())
                            .sellingPrice(BigDecimal.valueOf(auction.getFinalCost()))
                            .receiptTime(auctionEndTime)
                            .buyer(auction.getWinningBidder())
                            .seller(userRepository
                                    .findCreatorByAuctionId(auction.getId())
                                    .orElseThrow())
                            .product(auction.getProduct())
                            .deliveryType(deliveryTypeRepository
                                    .findById("STANDARD_DELIVERY")
                                    .orElseThrow())
                            .paymentType(paymentTypeRepository
                                    .findById("COD_PAYMENT")
                                    .orElseThrow())
                            .status(waitingForPaymentStatus)
                            .build();
                    receiptRepository.save(receipt);
                    log.info("Payment created for Auction {} with ID {}", auction.getId(), receipt.getId());

                    User seller = receipt.getSeller();
                    User buyer = receipt.getBuyer();

                    Product product = receipt.getProduct();

                    seller.getSalesReceipt().add(receipt);
                    buyer.getPurchaseReceipt().add(receipt);
                    buyer.getProductSuccessfullyAuctioned().add(product);

                    userRepository.save(seller);
                    userRepository.save(buyer);

                    product.setStatus(statusRepository.findById("PENDING_SOLD").orElseThrow());

                    productRepository.save(product);
                });
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

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateRemainingTimeForOngoingAuctions() {
        List<Auction> ongoingAuctions = auctionRepository.findByStatusName("ONGOING");

        ongoingAuctions.forEach(auction -> {
            LocalDateTime endTime =
                    auction.getStartTime().plus(auction.getTime().getTime());
            Duration remaining = Duration.between(LocalDateTime.now(), endTime);

            auction.setRemainingTime(remaining.isNegative() ? Duration.ZERO : remaining);

            auctionRepository.save(auction);
        });
    }

    @PreAuthorize("hasAuthority('GET_ALL_AUCTIONS_PENDING_OF_USER')")
    public AuctionPageResponse getAllAuctionsPendingPagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Auction> auctions = user.getAuctions();

        Status status = statusRepository
                .findById("PENDING")
                .orElseThrow(() -> new RuntimeException("Status PENDING not found"));

        List<Auction> filteredAuctions = auctions.stream()
                .filter(auction -> status.equals(auction.getStatus()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filteredAuctions.size());
        int end = Math.min((start + pageable.getPageSize()), filteredAuctions.size());
        Page<Auction> auctionPage =
                new PageImpl<>(filteredAuctions.subList(start, end), pageable, filteredAuctions.size());

        List<AuctionResponse> auctionResponses = auctionPage.getContent().stream()
                .map(auctionMapper::toAuctionResponse)
                .toList();

        return AuctionPageResponse.builder()
                .auctions(auctionResponses)
                .totalPages(auctionPage.getTotalPages())
                .totalElements(auctionPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_AUCTIONS_ONGOING_OF_USER')")
    public AuctionPageResponse getAllAuctionsOngoingPagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Auction> auctions = user.getAuctions();

        Status status = statusRepository
                .findById("ONGOING")
                .orElseThrow(() -> new RuntimeException("Status ONGOING not found"));

        List<Auction> filteredAuctions = auctions.stream()
                .filter(auction -> status.equals(auction.getStatus()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filteredAuctions.size());
        int end = Math.min((start + pageable.getPageSize()), filteredAuctions.size());
        Page<Auction> auctionPage =
                new PageImpl<>(filteredAuctions.subList(start, end), pageable, filteredAuctions.size());

        List<AuctionResponse> auctionResponses = auctionPage.getContent().stream()
                .map(auctionMapper::toAuctionResponse)
                .toList();

        return AuctionPageResponse.builder()
                .auctions(auctionResponses)
                .totalPages(auctionPage.getTotalPages())
                .totalElements(auctionPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_AUCTIONS_ENDED_OF_USER')")
    public AuctionPageResponse getAllAuctionsEndedPagedOfUser(int page, int size) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Auction> auctions = user.getAuctions();

        Status status =
                statusRepository.findById("ENDED").orElseThrow(() -> new RuntimeException("Status ENDED not found"));

        List<Auction> filteredAuctions = auctions.stream()
                .filter(auction -> status.equals(auction.getStatus()))
                .toList();

        Pageable pageable = PageRequest.of(page, size);

        int start = Math.min((int) pageable.getOffset(), filteredAuctions.size());
        int end = Math.min((start + pageable.getPageSize()), filteredAuctions.size());
        Page<Auction> auctionPage =
                new PageImpl<>(filteredAuctions.subList(start, end), pageable, filteredAuctions.size());

        List<AuctionResponse> auctionResponses = auctionPage.getContent().stream()
                .map(auctionMapper::toAuctionResponse)
                .toList();

        return AuctionPageResponse.builder()
                .auctions(auctionResponses)
                .totalPages(auctionPage.getTotalPages())
                .totalElements(auctionPage.getTotalElements())
                .build();
    }

    @PreAuthorize("hasAuthority('GET_ALL_AUCTIONS_OF_USER_JOINED')")
    public List<AuctionResponse> getAllAuctionsOfUserJoined() {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Set<Auction> joinedAuctions = user.getJoinedAuctions();

        return joinedAuctions.stream()
                .map(auction -> AuctionResponse.builder()
                        .id(auction.getId())
                        .name(auction.getName())
                        .product(ProductResponse.builder()
                                .id(auction.getProduct().getId())
                                .name(auction.getProduct().getName())
                                .build())
                        .startTime(auction.getStartTime())
                        .endTime(auction.getEndTime())
                        .status(StatusResponse.builder()
                                .name(auction.getStatus().getName())
                                .description(auction.getStatus().getDescription())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }
}
