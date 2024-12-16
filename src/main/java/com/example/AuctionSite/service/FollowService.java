package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Follow;
import com.example.AuctionSite.entity.User;
import com.example.AuctionSite.exception.AppException;
import com.example.AuctionSite.exception.ErrorCode;
import com.example.AuctionSite.repository.AuctionRepository;
import com.example.AuctionSite.repository.FollowRepository;
import com.example.AuctionSite.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FollowService {
    FollowRepository followRepository;
    UserService userService;
    UserRepository userRepository;
    AuctionRepository auctionRepository;

    @PreAuthorize("hasAuthority('ADD_FOLLOW_AUCTION')")
    public FollowResponse addFollowAuction(Integer auctionId) {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        Auction auction = auctionRepository.findById(auctionId).orElseThrow();

        if (!auction.getStatus().getName().equalsIgnoreCase("PENDING")) {
            throw new AppException(ErrorCode.INVALID_AUCTION_STATUS);
        }

        boolean alreadyFollowed = followRepository.existsByUserIdAndAuctionId(user.getId(), auction.getId());
        if (alreadyFollowed) {
            throw new AppException(ErrorCode.ALREADY_FOLLOWED);
        }

        Follow follow = Follow.builder().user(user).auction(auction).build();
        followRepository.save(follow);

        user.getFollows().add(follow);
        auction.getFollows().add(follow);

        return FollowResponse.builder()
                .id(follow.getId())
                .followed(follow.getAuction().getName())
                .build();
    }

    @PreAuthorize("hasAuthority('UNFOLLOW_AUCTION')")
    public void unfollowAuction(Integer auctionId) {
        String userId = userService.getUserId();

        Follow follow = followRepository
                .findByAuctionIdAndUserId(auctionId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.FOLLOW_NOT_FOUND_OR_NOT_AUTHORIZED));

        followRepository.delete(follow);
    }

    @PreAuthorize("hasAuthority('GET_ALL_FOLLOW_BY_USERID')")
    public List<FollowResponse> getAllFollowByUserid(String userid) {
        User user = userRepository.findById(userid).orElseThrow();

        List<Follow> follows = followRepository.findAllByUser(user);

        List<FollowResponse> followResponses = follows.stream()
                .map(follow -> FollowResponse.builder()
                        .id(follow.getId())
                        .followed(follow.getAuction().getName())
                        .build())
                .toList();

        if (followResponses.isEmpty()) {
            throw new AppException(ErrorCode.FOLLOW_NOT_FOUND_OR_NOT_AUTHORIZED);
        }

        return followResponses;
    }

    @PreAuthorize("hasAuthority('GET_ALL_FOLLOW_BY_AUCTIONID')")
    public List<FollowResponse> getAllFollowByAuctionid(Integer auctionid) {
        Auction auction = auctionRepository.findById(auctionid).orElseThrow();

        List<Follow> follows = followRepository.findAllByAuction(auction);

        List<FollowResponse> followResponses = follows.stream()
                .map(follow -> FollowResponse.builder()
                        .id(follow.getId())
                        .followed_by(follow.getUser().getUsername())
                        .build())
                .toList();

        if (followResponses.isEmpty()) {
            throw new AppException(ErrorCode.FOLLOW_NOT_FOUND_OR_NOT_AUTHORIZED);
        }

        return followResponses;
    }

    @PreAuthorize("hasAuthority('GET_ALL_FOLLOW_OF_USER')")
    public List<FollowResponse> getAllFollowsOfUser() {
        String userId = userService.getUserId();
        User user = userRepository.findById(userId).orElseThrow();

        List<Follow> follows = followRepository.findAllByUser(user);

        List<FollowResponse> followResponses = follows.stream()
                .map(follow -> FollowResponse.builder()
                        .id(follow.getId())
                        .followed(follow.getAuction().getName())
                        .build())
                .toList();

        if (followResponses.isEmpty()) {
            throw new AppException(ErrorCode.FOLLOW_NOT_FOUND_OR_NOT_FOLLOW);
        }

        return followResponses;
    }
}
