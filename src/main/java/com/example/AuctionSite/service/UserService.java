package com.example.AuctionSite.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.AuctionSite.dto.request.UserCreateRequest;
import com.example.AuctionSite.dto.request.UserUpdateRequest;
import com.example.AuctionSite.dto.response.UserResponse;
import com.example.AuctionSite.entity.*;
import com.example.AuctionSite.exception.AppException;
import com.example.AuctionSite.exception.ErrorCode;
import com.example.AuctionSite.mapper.UserMapper;
import com.example.AuctionSite.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    AuctionRepository auctionRepository;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    ProductRepository productRepository;
    RateRepository rateRepository;
    RanksRepository ranksRepository;
    StatusRepository statusRepository;

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByUsername(userCreateRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (!userCreateRequest.getPassword().equals(userCreateRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
        }

        User user = userMapper.toUser(userCreateRequest);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findById("USER").orElseThrow());

        Rate rate = rateRepository.findById(0F).orElseThrow();

        Ranks ranks = ranksRepository.findById("BRONZE").orElseThrow();

        Status status = statusRepository.findById("REGISTERED").orElseThrow();

        user.setRoles(role);
        user.setRate(rate);
        user.setRanks(ranks);
        user.setJoiningDate(LocalDate.now());
        user.setStatus(status);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    public List<UserResponse> getAllUsers() {
        return userMapper.toListUserResponse(userRepository.findAll());
    }

    @PreAuthorize("hasAuthority('GET_USER_BY_ID')")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @PreAuthorize("hasAuthority('GET_USER_BY_USERNAME')")
    public UserResponse getUserByUsername(String username) {
        return userMapper.toUserResponse(
                userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.toUpdateUser(user, userUpdateRequest);

        if (userUpdateRequest.getPassword() != null
                && !userUpdateRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getUsername().equals("admin")) {
            throw new AppException(ErrorCode.ADMIN_NOT_DELETE);
        }

        userRepository.deleteById(id);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public String getUserId() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return user.getId();
    }

    public void addProductToUser(String userId, Integer productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Product product = productRepository.findById(productId).orElseThrow();

        user.getProducts().add(product);
        userRepository.save(user);
    }

    public void addAuctionToUser(String userId, Integer auctionId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();

        user.getAuctions().add(auction);
        userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('GET_ALL_USERS_JOINED_BY_AUCTIONID_OF_USER')")
    public Set<UserResponse> getAuctionParticipantsOfUserCreate(Integer auctionId) {
        Auction auction =
                auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));

        User creator = userRepository
                .findCreatorByAuctionId(auctionId)
                .orElseThrow(() -> new AppException(ErrorCode.CREATOR_NOT_FOUND));

        String currentUserId = getUserId();

        if (!creator.getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.USER_IS_NOT_CERATOR);
        }

        return auction.getParticipants().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toSet());
    }

    @PreAuthorize("hasAuthority('GET_ALL_USERS_JOINED_BY_AUCTIONID')")
    public Set<UserResponse> getAuctionParticipants(Integer auctionId) {
        Auction auction =
                auctionRepository.findById(auctionId).orElseThrow(() -> new RuntimeException("Auction not found"));

        return auction.getParticipants().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toSet());
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateOnlineUserActionTime() {
        Status onlineStatus = statusRepository.findById("ONLINE").orElseThrow();

        var onlineUsers = userRepository.findByStatus(onlineStatus);

        for (User user : onlineUsers) {
            user.setActionTime(user.getActionTime().plusMinutes(1));
            userRepository.save(user);
            log.info("Updated actionTime for user: " + user.getUsername());
        }
    }
}
