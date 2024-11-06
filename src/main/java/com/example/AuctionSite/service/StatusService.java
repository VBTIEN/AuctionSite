package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.StatusRequest;
import com.example.AuctionSite.dto.response.StatusResponse;
import com.example.AuctionSite.entity.Status;
import com.example.AuctionSite.mapper.StatusMapper;
import com.example.AuctionSite.repository.StatusRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatusService {
    StatusRepository statusRepository;
    StatusMapper statusMapper;

    @PreAuthorize("hasAuthority('CREATE_STATUS')")
    public StatusResponse createStatus(StatusRequest statusRequest) {
        Status status = statusMapper.toStatus(statusRequest);
        return statusMapper.toStatusResponse(statusRepository.save(status));
    }

    @PreAuthorize("hasAuthority('GET_ALL_STATUS')")
    public List<StatusResponse> getAllStatus() {
        return statusRepository.findAll().stream()
                .map(statusMapper::toStatusResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_STATUS_BY_NAME')")
    public StatusResponse getStatusByName(String name) {
        Status status = statusRepository.findById(name).orElseThrow(() -> new RuntimeException("Status not found"));
        return statusMapper.toStatusResponse(status);
    }

    @PreAuthorize("hasAuthority('UPDATE_STATUS')")
    public StatusResponse updateStatus(String name, StatusRequest statusRequest) {
        Status status = statusRepository.findById(name).orElseThrow(() -> new RuntimeException("Status not found"));
        statusMapper.toUpdateStatus(status, statusRequest);
        return statusMapper.toStatusResponse(statusRepository.save(status));
    }

    @PreAuthorize("hasAuthority('DELETE_STATUS')")
    public void deleteStatus(String name) {
        statusRepository.deleteById(name);
    }
}
