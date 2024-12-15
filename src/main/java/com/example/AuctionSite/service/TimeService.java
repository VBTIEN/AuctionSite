package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.TimeRequest;
import com.example.AuctionSite.dto.response.TimeResponse;
import com.example.AuctionSite.entity.Time;
import com.example.AuctionSite.mapper.TimeMapper;
import com.example.AuctionSite.repository.TimeRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeService {
    TimeRepository timeRepository;
    TimeMapper timeMapper;

    @PreAuthorize("hasAuthority('CREATE_TIME')")
    public TimeResponse createTime(TimeRequest timeRequest) {
        Time time = timeMapper.toTime(timeRequest);
        return timeMapper.toTimeResponse(timeRepository.save(time));
    }

    public List<TimeResponse> getAllTimes() {
        return timeRepository.findAll().stream().map(timeMapper::toTimeResponse).toList();
    }

    @PreAuthorize("hasAuthority('GET_TIME_BY_NAME')")
    public TimeResponse getTimeByName(String name) {
        Time time = timeRepository.findById(name).orElseThrow(() -> new RuntimeException("Time not found"));
        return timeMapper.toTimeResponse(time);
    }

    @PreAuthorize("hasAuthority('UPDATE_TIME')")
    public TimeResponse updateTime(String name, TimeRequest timeRequest) {
        Time time = timeRepository.findById(name).orElseThrow(() -> new RuntimeException("Time not found"));
        timeMapper.toUpdateTime(time, timeRequest);
        return timeMapper.toTimeResponse(timeRepository.save(time));
    }

    @PreAuthorize("hasAuthority('DELETE_TIME')")
    public void deleteTime(String name) {
        timeRepository.deleteById(name);
    }
}
