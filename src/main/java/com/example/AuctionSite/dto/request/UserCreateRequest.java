package com.example.AuctionSite.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotBlank(message = "USERNAME_BLANk")
    @Size(min = 5, max = 20, message = "USERNAME_INVALIDSIZE")
    String username;
    
    @NotBlank(message = "PASSWORD_BLANK")
    @Size(min = 8, message = "PASSWORD_INVALIDSIZE")
    String password;
    
    @NotBlank(message = "EMAIL_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;
    
    @Past(message = "")
    LocalDate dob;
    
    
    @NotBlank(message = "")
    @Size(max = 50, message = "")
    String fullName;
}
