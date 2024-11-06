package com.example.AuctionSite.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.FollowRequest;
import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.entity.Auction;
import com.example.AuctionSite.entity.Follow;
import com.example.AuctionSite.entity.User;
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
    UserRepository userRepository;
    AuctionRepository auctionRepository;

    @PreAuthorize("hasAuthority('ADD_FOLLOW_AUCTION')")
    public FollowResponse addFollowAuction(FollowRequest followRequest) {
        User user = userRepository.findById(followRequest.getUserId()).orElseThrow();
        Auction auction =
                auctionRepository.findById(followRequest.getAuctionId()).orElseThrow();

        Follow follow = Follow.builder().user(user).auction(auction).build();
        followRepository.save(follow);

        user.getFollows().add(follow);
        auction.getFollows().add(follow);

        return FollowResponse.builder().followed(follow.getAuction().getName()).build();
    }

    @PreAuthorize("hasAuthority('UNFOLLOW_AUCTION')")
    public void unfollowAuction(Integer id) {
        followRepository.deleteById(id);
    }
}
