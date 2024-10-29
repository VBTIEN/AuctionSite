package com.example.AuctionSite.mapper;

import com.example.AuctionSite.dto.response.FollowResponse;
import com.example.AuctionSite.entity.Follow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    FollowResponse toFollowResponse(Follow follow);
}
