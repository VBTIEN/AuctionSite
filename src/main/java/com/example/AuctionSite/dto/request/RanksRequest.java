package com.example.AuctionSite.dto.request;

import java.time.Duration;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RanksRequest {
    @NotBlank(message = "RANKSNAME_BLANK")
    @Size(max = 50, message = "RANKSNAME_INVALID_SIZE")
    String name;

    @NotBlank(message = "RANKS_DESCRIPTION_BLANK")
    String description;

    // Tổng số lần người dùng mua hoặc bán thành công sản phẩm
    @NotBlank(message = "SUCCESSFULTRANSACTIONS_BLANK")
    Integer successfulTransactions;

    // Thời gian người dùng đã tham gia vào website, tính bằng ngày
    @NotBlank(message = "MEMBERSHIPDURATION_BLANK")
    Duration membershipDuration;

    // Tần suất tham gia, chẳng hạn như số lần đăng nhập, tham gia đấu giá, và giao dịch trong khoảng thời gian
    @NotBlank(message = "ACTIVITYFREQUENCY_BLANK")
    Integer activityFrequency;

    @NotBlank(message = "RANKS_BENEFITS_BLANK")
    Set<String> benefits;
}
