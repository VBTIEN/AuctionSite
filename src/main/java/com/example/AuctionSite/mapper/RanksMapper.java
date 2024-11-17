package com.example.AuctionSite.mapper;

import java.time.Duration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.RanksRequest;
import com.example.AuctionSite.dto.response.RanksResponse;
import com.example.AuctionSite.entity.Ranks;

@Mapper(componentModel = "spring")
public interface RanksMapper {
    @Mapping(target = "benefits", ignore = true)
    @Mapping(target = "membershipDuration", source = "membershipDuration", qualifiedByName = "stringToDuration")
    Ranks toRanks(RanksRequest ranksRequest);

    @Mapping(target = "membershipDuration", source = "membershipDuration", qualifiedByName = "durationToString")
    RanksResponse toRanksResponse(Ranks ranks);

    @Mapping(target = "benefits", ignore = true)
    @Mapping(target = "membershipDuration", source = "membershipDuration", qualifiedByName = "stringToDuration")
    void toUpdateRank(@MappingTarget Ranks ranks, RanksRequest ranksRequest);

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
