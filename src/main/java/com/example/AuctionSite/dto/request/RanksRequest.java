package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RanksRequest {
    @NotBlank(message = "")
    String name;
    
    @NotBlank(message = "")
    String description;
    
    // Tổng số lần người dùng mua hoặc bán thành công sản phẩm
    @NotBlank(message = "")
    Integer successfulTransactions;
    
    // Thời gian người dùng đã tham gia vào website, tính bằng ngày
    @NotBlank(message = "")
    Duration membershipDuration;
    
    // Tần suất tham gia, chẳng hạn như số lần đăng nhập, tham gia đấu giá, và giao dịch trong khoảng thời gian
    @NotBlank(message = "")
    Integer activityFrequency;
    
    @NotBlank(message = "")
    Set<String> benefits;
}
