package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.BenefitRequest;
import com.example.AuctionSite.dto.response.BenefitResponse;
import com.example.AuctionSite.entity.Benefit;
import com.example.AuctionSite.mapper.BenefitMapper;
import com.example.AuctionSite.repository.BenefitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BenefitService {
    BenefitRepository benefitRepository;
    BenefitMapper benefitMapper;
    
    @PreAuthorize("hasAuthority('CREATE_BENEFIT')")
    public BenefitResponse createBenefit(BenefitRequest benefitRequest) {
        Benefit benefit = benefitMapper.toBenefit(benefitRequest);
        return benefitMapper.toBenefitResponse(benefitRepository.save(benefit));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_BENEFITS')")
    public List<BenefitResponse> getAllBenefits() {
        return benefitRepository.findAll().stream().map(benefitMapper::toBenefitResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('GET_BENEFIT_BY_NAME')")
    public BenefitResponse getBenefitByName(String name) {
        Benefit benefit = benefitRepository.findById(name).orElseThrow(() -> new RuntimeException("Benefit not found"));
        return benefitMapper.toBenefitResponse(benefit);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_BENEFIT')")
    public BenefitResponse updateBenefit(String id, BenefitRequest benefitRequest) {
        Benefit benefit = benefitRepository.findById(id).orElseThrow(() -> new RuntimeException("Benefit not found"));
        benefitMapper.toUpdateBenefit(benefit, benefitRequest);
        return benefitMapper.toBenefitResponse(benefitRepository.save(benefit));
    }
    
    @PreAuthorize("hasAuthority('DELETE_BENEFIT')")
    public void deleteBenefit(String id) {
        benefitRepository.deleteById(id);
    }
}
