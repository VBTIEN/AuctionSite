package com.example.AuctionSite.dto.request;

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
public class RoleRequest {
    @NotBlank(message = "ROLENAME_BLANK")
    @Size(max = 50, message = "ROLENAME_INVALID_SIZE")
    String name;

    @NotBlank(message = "ROLE_DESCRIPTION_BLANK")
    String description;

    @NotBlank(message = "ROLE_PERMISSIONS_BLANK")
    Set<String> permissions;
}
