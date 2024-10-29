package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.mapper.FollowMapper;
import com.example.AuctionSite.repository.FollowRepository;
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
public class FollowService {
    FollowRepository followRepository;
    FollowMapper followMapper;
    
    
}
