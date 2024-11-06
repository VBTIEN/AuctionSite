package com.example.AuctionSite.entity;

import java.time.Duration;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ranks {
    @Id
    String name;

    String description;
    // Tổng số lần người dùng mua hoặc bán thành công sản phẩm
    Integer successfulTransactions;
    // Thời gian người dùng đã tham gia vào website, tính bằng ngày
    Duration membershipDuration;
    // Tần suất tham gia, chẳng hạn như số lần đăng nhập, tham gia đấu giá, và giao dịch trong khoảng thời gian
    Integer activityFrequency;

    @ManyToMany
    @ToString.Exclude
    Set<Benefit> benefits;

    @OneToMany
    @ToString.Exclude
    Set<User> users;
}
