package com.example.AuctionSite.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotBlank(message = "USERNAME_BLANk")
    @Size(min = 5, max = 20, message = "USERNAME_INVALID_SIZE")
    String username;

    @NotBlank(message = "PASSWORD_BLANK")
    @Size(min = 8, message = "PASSWORD_INVALID_SIZE")
    String password;

    @NotBlank(message = "CONFIRM_PASSWORD_BLANK")
    @Size(min = 8, message = "CONFIRM_PASSWORD_INVALID_SIZE")
    String confirmPassword;

    @NotBlank(message = "EMAIL_BLANK")
    @Email(message = "EMAIL_INVALID")
    String email;

    @Past(message = "DOB_NOT_IN_THE_PAST")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate dob;

    @NotBlank(message = "FULLNAME_BLANK")
    @Size(max = 50, message = "FULLNAME_INVALID_SIZE")
    String fullName;

    @NotBlank(message = "PHONENUMBER_BLANK")
    String phoneNumber;

    @NotBlank(message = "ADDRESS_BLANK")
    String address;
}
