package com.example.AuctionSite.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RanksResponse {
    String name;
    String description;
    // Tổng số lần người dùng mua hoặc bán thành công sản phẩm
    Integer successfulTransactions;
    // Thời gian người dùng đã tham gia vào website, tính bằng ngày
    Duration membershipDuration;
    // Tần suất tham gia, chẳng hạn như số lần đăng nhập, tham gia đấu giá, và giao dịch trong khoảng thời gian
    Integer activityFrequency;
    
    Set<BenefitResponse> benefits;
}
