package com.example.AuctionSite.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.RanksRequest;
import com.example.AuctionSite.dto.response.RanksResponse;
import com.example.AuctionSite.entity.Ranks;
import com.example.AuctionSite.mapper.RanksMapper;
import com.example.AuctionSite.repository.BenefitRepository;
import com.example.AuctionSite.repository.RanksRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RanksService {
    RanksRepository ranksRepository;
    RanksMapper ranksMapper;
    BenefitRepository benefitRepository;

    @PreAuthorize("hasAuthority('CREATE_RANK')")
    public RanksResponse createRank(RanksRequest ranksRequest) {
        Ranks ranks = ranksMapper.toRanks(ranksRequest);
        var benefits = benefitRepository.findAllById(ranksRequest.getBenefits());
        ranks.setBenefits(new HashSet<>(benefits));
        return ranksMapper.toRanksResponse(ranksRepository.save(ranks));
    }

    @PreAuthorize("hasAuthority('GET_ALL_RANKS')")
    public List<RanksResponse> getAllRanks() {
        var ranks = ranksRepository.findAll();
        return ranks.stream().map(ranksMapper::toRanksResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_RANK_BY_NAME')")
    public RanksResponse getRankByName(String name) {
        Ranks ranks = ranksRepository.findById(name).orElseThrow(() -> new RuntimeException("Rank not found"));
        return ranksMapper.toRanksResponse(ranks);
    }

    @PreAuthorize("hasAuthority('UPDATE_RANK')")
    public RanksResponse updateRank(String name, RanksRequest ranksRequest) {
        Ranks ranks = ranksRepository.findById(name).orElseThrow(() -> new RuntimeException("Rank not found"));
        ranksMapper.toUpdateRank(ranks, ranksRequest);
        var benefits = benefitRepository.findAllById(ranksRequest.getBenefits());
        ranks.setBenefits(new HashSet<>(benefits));
        return ranksMapper.toRanksResponse(ranksRepository.save(ranks));
    }

    @PreAuthorize("hasAuthority('DELETE_RANK')")
    public void deleteRank(String name) {
        ranksRepository.deleteById(name);
    }
}
