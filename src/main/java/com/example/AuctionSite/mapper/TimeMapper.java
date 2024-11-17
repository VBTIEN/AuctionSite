package com.example.AuctionSite.mapper;

import java.time.Duration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.TimeRequest;
import com.example.AuctionSite.dto.response.TimeResponse;
import com.example.AuctionSite.entity.Time;

@Mapper(componentModel = "spring")
public interface TimeMapper {
    @Mapping(target = "time", source = "time", qualifiedByName = "stringToDuration")
    Time toTime(TimeRequest timeRequest);

    @Mapping(target = "time", source = "time", qualifiedByName = "durationToString")
    TimeResponse toTimeResponse(Time time);

    @Mapping(target = "time", source = "time", qualifiedByName = "stringToDuration")
    void toUpdateTime(@MappingTarget Time time, TimeRequest timeRequest);

    @org.mapstruct.Named("stringToDuration")
    default Duration stringToDuration(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            return Duration.ZERO;
        }
        String[] parts = timeString.split(":");
        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        return Duration.ofHours(hours).plusMinutes(minutes);
    }

    @org.mapstruct.Named("durationToString")
    default String durationToString(Duration duration) {
        if (duration == null) {
            return "00:00";
        }
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%02d:%02d", hours, minutes);
    }
}
