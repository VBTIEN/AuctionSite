package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.UserCreateRequest;
import com.example.AuctionSite.dto.request.UserUpdateRequest;
import com.example.AuctionSite.dto.response.UserResponse;
import com.example.AuctionSite.entity.Role;
import com.example.AuctionSite.entity.User;
import com.example.AuctionSite.exception.AppException;
import com.example.AuctionSite.enums.ErrorCode;
import com.example.AuctionSite.mapper.UserMapper;
import com.example.AuctionSite.repository.RoleRepository;
import com.example.AuctionSite.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByUsername(userCreateRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreateRequest);
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findById("USER").get());
        user.setRoles(role);
        user.setJoiningDate(LocalDate.now());
        return userMapper.toUserResponse(userRepository.save(user));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    public List<UserResponse> getAllUsers() {
        return userMapper.toListUserResponse(userRepository.findAll());
    }
    
    @PreAuthorize("hasAuthority('GET_USER_BY_ID')")
    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }
    
    @PreAuthorize("hasAuthority('GET_USER_BY_USERNAME')")
    public UserResponse getUserByUsername(String username) {
        return userMapper.toUserResponse(userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));
    }
    
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() ->
            new AppException(ErrorCode.USER_NOTEXISTED));
        return userMapper.toUserResponse(user);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, userUpdateRequest);
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }
    
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
