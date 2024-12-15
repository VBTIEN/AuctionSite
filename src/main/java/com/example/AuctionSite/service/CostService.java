package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.CostRequest;
import com.example.AuctionSite.dto.response.CostResponse;
import com.example.AuctionSite.entity.Cost;
import com.example.AuctionSite.mapper.CostMapper;
import com.example.AuctionSite.repository.CostRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CostService {
    CostRepository costRepository;
    CostMapper costMapper;

    @PreAuthorize("hasAuthority('CREATE_COST')")
    public CostResponse createCost(CostRequest costRequest) {
        Cost cost = costMapper.toCost(costRequest);
        return costMapper.toCostResponse(costRepository.save(cost));
    }

    public List<CostResponse> getAllCosts() {
        return costRepository.findAll().stream().map(costMapper::toCostResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_COST_BY_NAME')")
    public CostResponse getCostByName(String name) {
        Cost cost = costRepository.findById(name).orElseThrow(() -> new RuntimeException("Cost not found"));
        return costMapper.toCostResponse(cost);
    }

    @PreAuthorize("hasAuthority('UPDATE_COST')")
    public CostResponse updateCost(String name, CostRequest costRequest) {
        Cost cost = costRepository.findById(name).orElseThrow(() -> new RuntimeException("Cost not found"));
        costMapper.toUpdateCost(cost, costRequest);
        return costMapper.toCostResponse(costRepository.save(cost));
    }

    @PreAuthorize("hasAuthority('DELETE_COST')")
    public void deleteCost(String name) {
        costRepository.deleteById(name);
    }
}
