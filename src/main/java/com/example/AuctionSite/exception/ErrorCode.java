package com.example.AuctionSite.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1,"Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(2,"Key invalid", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(3,"Token invalid", HttpStatus.BAD_REQUEST),
    
    USER_EXISTED(1000,"User existed", HttpStatus.BAD_REQUEST),
    USER_NOTEXISTED(1001,"User not existed", HttpStatus.NOT_FOUND),
    USER_UNAUTHENTICATED(1002, "User unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_FORBIDDEN(1003, "You don't have permission", HttpStatus.FORBIDDEN),
    USERNAME_BLANK(1004,"Username must be not blank", HttpStatus.BAD_REQUEST),
    USERNAME_INVALIDSIZE(1005,"Username must be at least 5 characters and at most 20 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_BLANK(1006,"Password must be not blank", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALIDSIZE(1007,"Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    EMAIL_BLANK(1008,"Email must be not blank", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1009,"Email is not in correct format", HttpStatus.BAD_REQUEST),
    
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
