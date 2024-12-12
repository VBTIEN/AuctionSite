package com.example.AuctionSite.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.BidRequest;
import com.example.AuctionSite.dto.response.BidRankingResponse;
import com.example.AuctionSite.dto.response.BidResponse;
import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Bid;
import com.example.AuctionSite.entity.User;
import com.example.AuctionSite.exception.AppException;
import com.example.AuctionSite.exception.ErrorCode;
import com.example.AuctionSite.mapper.BidMapper;
import com.example.AuctionSite.repository.AuctionRepository;
import com.example.AuctionSite.repository.BidRepository;
import com.example.AuctionSite.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BidService {
    BidRepository bidRepository;
    BidMapper bidMapper;
    UserService userService;
    UserRepository userRepository;
    AuctionRepository auctionRepository;

    @PreAuthorize("hasAuthority('CREATE_BID')")
    public BidResponse createBid(Integer auctionId, BidRequest bidRequest) throws AppException {
        Bid bid = bidMapper.toBid(bidRequest);

        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Auction auction = auctionRepository.findById(auctionId).orElseThrow();

        if (!auction.getStatus().getName().equals("ONGOING")) {
            throw new AppException(ErrorCode.AUCTION_NOT_ONGOING);
        }

        User creator = userRepository
                .findCreatorByAuctionId(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.CREATOR_NOT_FOUND));

        if (creator.getId().equals(userId)) {
            throw new AppException(ErrorCode.CREATOR_CANNOT_BID);
        }

        BigDecimal initialCost = auction.getCost().getStartCost();
        BigDecimal stepAmount = auction.getStep().getStep();

        Optional<Bid> highestBidOptional = bidRepository.findTopByAuctionOrderByBidMountDesc(auction);

        BigDecimal minimumBid;
        if (highestBidOptional.isPresent()) {
            Bid highestBid = highestBidOptional.get();

            if (highestBid.getUser().getId().equals(userId)) {
                throw new AppException(ErrorCode.CONSECUTIVE_BID_NOT_ALLOWED);
            }

            BigDecimal highestBidAmount = highestBid.getBidMount();
            minimumBid = highestBidAmount.add(stepAmount);
        } else {
            minimumBid = initialCost.add(stepAmount);
        }

        if (bidRequest.getBidMount().compareTo(minimumBid) < 0) {
            throw new AppException(ErrorCode.BIDMOUNT_INVALID);
        }

        if (!auction.getParticipants().contains(user)) {
            auction.getParticipants().add(user);
            auction.setParticipantCount(auction.getParticipants().size());
        }

        bid.setUser(user);
        bid.setAuction(auction);

        bidRepository.save(bid);

        int currentNumberOfBids = auction.getNumberOfBids() != null ? auction.getNumberOfBids() : 0;
        auction.setNumberOfBids(currentNumberOfBids + 1);

        auctionRepository.save(auction);

        BidResponse bidResponse = bidMapper.toBidResponse(bid);
        bidResponse.setResult(bid.getUser().getUsername() + ": " + bidResponse.getBidMount());

        return bidResponse;
    }

    @PreAuthorize("hasAuthority('GET_ALL_BIDS_BY_AUCTIONID')")
    public List<BidResponse> getAllBidsByAuctionId(Integer auctionId) {
        List<Bid> bids = bidRepository.findAllByAuctionId(auctionId);

        List<BidResponse> bidResponses = bids.stream()
                .map(bid -> BidResponse.builder()
                        .id(bid.getId())
                        .bidMount(bid.getBidMount())
                        .by_user(bid.getUser().getUsername())
                        .build())
                .toList();

        if (bidResponses.isEmpty()) {
            throw new AppException(ErrorCode.BID_NOT_FOUND_OR_NOT_AUTHORIZED);
        }

        return bidResponses;
    }

    @PreAuthorize("hasAuthority('GET_ALL_BIDS_BY_USERID')")
    public List<BidResponse> getAllBidsByUserId(String userId) {
        List<Bid> bids = bidRepository.findAllByUserId(userId);

        List<BidResponse> bidResponses = bids.stream()
                .map(bid -> BidResponse.builder()
                        .id(bid.getId())
                        .bidMount(bid.getBidMount())
                        .bid_of(bid.getAuction().getName())
                        .build())
                .toList();

        if (bidResponses.isEmpty()) {
            throw new AppException(ErrorCode.BID_NOT_FOUND_OR_NOT_AUTHORIZED);
        }

        return bidResponses;
    }

    @PreAuthorize("hasAuthority('GET_ALL_BIDS_OF_USER')")
    public List<BidResponse> getAllBidsOfUser() {
        String userId = userService.getUserId();

        List<Bid> bids = bidRepository.findAllByUserId(userId);

        return bids.stream()
                .map(bid -> BidResponse.builder()
                        .id(bid.getId())
                        .bidMount(bid.getBidMount())
                        .bid_of(bid.getAuction().getName())
                        .build())
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_ALL_BIDS_BY_AUCTIONID_OF_USER')")
    public List<BidResponse> getAllBidsByAuctionIdOfUser(Integer auctionId) {
        String userId = userService.getUserId();

        List<Bid> bids = bidRepository.findByAuctionIdAndUserId(auctionId, userId);

        List<BidResponse> bidResponses = bids.stream()
                .map(bid -> BidResponse.builder()
                        .id(bid.getId())
                        .bidMount(bid.getBidMount())
                        .bid_of(bid.getAuction().getName())
                        .build())
                .toList();

        if (bidResponses.isEmpty()) {
            throw new AppException(ErrorCode.BID_NOT_FOUND_OR_NOT_AUTHORIZED);
        }

        return bidResponses;
    }

    public List<BidRankingResponse> getBidRankingForAuction(Integer auctionId) {
        List<Bid> bids = bidRepository.findBidsByAuctionIdOrderByBidMountDesc(auctionId);
        Map<String, BidRankingResponse> userHighestBids = new LinkedHashMap<>();
        int rank = 1;

        for (Bid bid : bids) {
            String userId = bid.getUser().getId();
            if (!userHighestBids.containsKey(userId)) {
                BidRankingResponse rankingDTO =
                        new BidRankingResponse(userId, bid.getUser().getUsername(), bid.getBidMount(), rank++);
                userHighestBids.put(userId, rankingDTO);
            }
        }

        return new ArrayList<>(userHighestBids.values());
    }
}
