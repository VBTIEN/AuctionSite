package com.example.AuctionSite.service;

import com.example.AuctionSite.dto.request.RateRequest;
import com.example.AuctionSite.dto.response.RateResponse;
import com.example.AuctionSite.entity.Rate;
import com.example.AuctionSite.mapper.RateMapper;
import com.example.AuctionSite.repository.RateRepository;
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
public class RateService {
    RateRepository rateRepository;
    RateMapper rateMapper;
    
    @PreAuthorize("hasAuthority('CREATE_RATE')")
    public RateResponse createRate(RateRequest rateRequest) {
        Rate rate = rateMapper.toRate(rateRequest);
        return rateMapper.toRateResponse(rateRepository.save(rate));
    }
    
    @PreAuthorize("hasAuthority('GET_ALL_RATES')")
    public List<RateResponse> getAllRates() {
        var rate = rateRepository.findAll();
        return rate.stream().map(rateMapper::toRateResponse).toList();
    }
    
    @PreAuthorize("hasAuthority('GET_RATE_BY_NUMBEROFSTAR')")
    public RateResponse getRateByNumberOfStar(Float number) {
        Rate rate = rateRepository.findById(number).orElseThrow(() -> new RuntimeException("Rate not found"));
        return rateMapper.toRateResponse(rate);
    }
    
    @PreAuthorize("hasAuthority('UPDATE_RATE')")
    public RateResponse updateRate(Float number, RateRequest rateRequest) {
        Rate rate = rateRepository.findById(number).orElseThrow(() -> new RuntimeException("Rate not found"));
        rateMapper.toUpdateRate(rate, rateRequest);
        return rateMapper.toRateResponse(rateRepository.save(rate));
    }
    
    @PreAuthorize("hasAuthority('DELETE_RATE')")
    public void deleteRate(Float number) {
        rateRepository.deleteById(number);
    }
}
