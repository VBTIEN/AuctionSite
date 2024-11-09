package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactRequest {
    @NotBlank(message = "ADDRESS_BLANK")
    String address;

    @NotBlank(message = "PHONENUMBER_BLANK")
    String phoneNumber;

    @NotBlank(message = "EMAIL_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;
}
