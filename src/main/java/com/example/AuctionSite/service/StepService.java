package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.StepRequest;
import com.example.AuctionSite.dto.response.StepResponse;
import com.example.AuctionSite.entity.Step;
import com.example.AuctionSite.mapper.StepMapper;
import com.example.AuctionSite.repository.StepRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StepService {
    StepRepository stepRepository;
    StepMapper stepMapper;

    @PreAuthorize("hasAuthority('CREATE_STEP')")
    public StepResponse createStep(StepRequest stepRequest) {
        Step step = stepMapper.toStep(stepRequest);
        return stepMapper.toStepResponse(stepRepository.save(step));
    }

    @PreAuthorize("hasAuthority('GET_ALL_STEPS')")
    public List<StepResponse> getAllSteps() {
        return stepRepository.findAll().stream().map(stepMapper::toStepResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_STEP_BY_NAME')")
    public StepResponse getStepByName(String name) {
        Step step = stepRepository.findById(name).orElseThrow(() -> new RuntimeException("Step not found"));
        return stepMapper.toStepResponse(step);
    }

    @PreAuthorize("hasAuthority('UPDATE_STEP')")
    public StepResponse updateStep(String name, StepRequest stepRequest) {
        Step step = stepRepository.findById(name).orElseThrow(() -> new RuntimeException("Step not found"));
        stepMapper.toUpdateStep(step, stepRequest);
        return stepMapper.toStepResponse(stepRepository.save(step));
    }

    @PreAuthorize("hasAuthority('DELETE_STEP')")
    public void deleteStep(String name) {
        stepRepository.deleteById(name);
    }
}
