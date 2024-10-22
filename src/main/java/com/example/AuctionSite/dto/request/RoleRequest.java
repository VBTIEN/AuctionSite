package com.example.AuctionSite.dto.request;

import com.example.AuctionSite.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Type;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    String name;
    String description;
    Set<String> permissions;
}
