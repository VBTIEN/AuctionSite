package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.RegulationRequest;
import com.example.AuctionSite.dto.response.RegulationResponse;
import com.example.AuctionSite.entity.Regulation;
import com.example.AuctionSite.mapper.RegulationMapper;
import com.example.AuctionSite.repository.RegulationRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegulationService {
    RegulationRepository regulationRepository;
    RegulationMapper regulationMapper;

    @PreAuthorize("hasAuthority('CREATE_REGULATION')")
    public RegulationResponse createRegulation(RegulationRequest regulationRequest) {
        Regulation regulation = regulationMapper.toRegulation(regulationRequest);
        return regulationMapper.toRegulationResponse(regulationRepository.save(regulation));
    }

    @PreAuthorize("hasAuthority('GET_ALL_REGULATIONS')")
    public List<RegulationResponse> getAllRegulations() {
        return regulationRepository.findAll().stream()
                .map(regulationMapper::toRegulationResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_REGULATION_BY_NAME')")
    public RegulationResponse getRegulationByName(String name) {
        Regulation regulation = regulationRepository.findById(name).orElseThrow();
        return regulationMapper.toRegulationResponse(regulation);
    }

    @PreAuthorize("hasAuthority('UPDATE_REGULATION')")
    public RegulationResponse updateRegulation(String name, RegulationRequest regulationRequest) {
        Regulation regulation = regulationRepository.findById(name).orElseThrow();
        regulationMapper.toUpdateRegulation(regulation, regulationRequest);
        return regulationMapper.toRegulationResponse(regulationRepository.save(regulation));
    }

    @PreAuthorize("hasAuthority('DELETE_REGULATION')")
    public void deleteRegulation(String name) {
        regulationRepository.deleteById(name);
    }
}
