package com.example.AuctionSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(2, "Key invalid", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(3, "Token invalid", HttpStatus.BAD_REQUEST),
    TOKEN_BLANK(4, "Token must be not blank", HttpStatus.BAD_REQUEST),
    // User
    USER_EXISTED(100, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(101, "User not existed", HttpStatus.NOT_FOUND),
    USER_UNAUTHENTICATED(102, "User unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_FORBIDDEN(103, "You don't have permission", HttpStatus.FORBIDDEN),
    USERNAME_BLANK(104, "Username must be not blank", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID_SIZE(
            105, "Username must be at least 5 characters and at most 20 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_BLANK(106, "Password must be not blank", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID_SIZE(107, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    EMAIL_BLANK(108, "Email must be not blank", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(109, "Email is not in correct format", HttpStatus.BAD_REQUEST),
    DOB_NOT_IN_THE_PAST(110, "Date of birth must be in the past", HttpStatus.BAD_REQUEST),
    FULLNAME_BLANK(111, "Fullname must be not blank", HttpStatus.BAD_REQUEST),
    FULLNAME_INVALID_SIZE(112, "Fullname must be at most 50 characters", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_BLANK(113, "Confirm password must be at most 50 characters", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_INVALID_SIZE(114, "Confirm password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    PASSWORDS_DO_NOT_MATCH(115, "Password must be match", HttpStatus.BAD_REQUEST),
    // Time
    TIMENAME_BLANK(200, "Name must be not blank", HttpStatus.BAD_REQUEST),
    TIMENAME_INVALID_SIZE(201, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    TIME_BLANK(202, "Time must be not blank", HttpStatus.BAD_REQUEST),
    // Step
    STEPNAME_BLANK(300, "Name must be not blank", HttpStatus.BAD_REQUEST),
    STEPNAME_INVALID_SIZE(301, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    STEP_BLANK(302, "Step must be not blank", HttpStatus.BAD_REQUEST),
    // Status
    STATUSNAME_BLANK(400, "Name must be not blank", HttpStatus.BAD_REQUEST),
    STATUSNAME_INVALID_SIZE(401, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    STATUS_DESCRIPTION_BLANK(402, "Description must be not blank", HttpStatus.BAD_REQUEST),
    // Role
    ROLENAME_BLANK(500, "Name must be not blank", HttpStatus.BAD_REQUEST),
    ROLENAME_INVALID_SIZE(501, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    ROLE_DESCRIPTION_BLANK(502, "Description must be not blank", HttpStatus.BAD_REQUEST),
    ROLE_PERMISSIONS_BLANK(503, "Permissions must be not blank", HttpStatus.BAD_REQUEST),
    // Rate
    NUMBEROFSTAR_BLANK(600, "Number of star must be not blank", HttpStatus.BAD_REQUEST),
    RATE_DESCRIPTION_BLANK(601, "Description must be not blank", HttpStatus.BAD_REQUEST),
    // Ranks
    RANKSNAME_BLANK(700, "Name must be not blank", HttpStatus.BAD_REQUEST),
    RANKSNAME_INVALID_SIZE(701, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    RANKS_DESCRIPTION_BLANK(702, "Description must be not blank", HttpStatus.BAD_REQUEST),
    SUCCESSFULTRANSACTIONS_BLANK(703, "Successful transactions must be not blank", HttpStatus.BAD_REQUEST),
    MEMBERSHIPDURATION_BLANK(704, "Membership duration must be not blank", HttpStatus.BAD_REQUEST),
    ACTIVITYFREQUENCY_BLANK(705, "Activity frequency must be not blank", HttpStatus.BAD_REQUEST),
    RANKS_BENEFITS_BLANK(706, "Benefits must be not blank", HttpStatus.BAD_REQUEST),
    // Product
    PRODUCTNAME_BLANK(800, "Name must be not blank", HttpStatus.BAD_REQUEST),
    PRODUCTNAME_INVALID_SIZE(801, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    PRODUCT_DESCRIPTION_BLANK(802, "Description must be at most 50 characters", HttpStatus.BAD_REQUEST),
    PRODUCT_CATEGORIES_BLANK(803, "Category must be not blank", HttpStatus.BAD_REQUEST),
    // Permission
    PERMISSIONNAME_BLANK(900, "Name must be not blank", HttpStatus.BAD_REQUEST),
    PERMISSIONNAME_INVALID_SIZE(901, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    PERMISSION_DESCRIPTION_BLANK(902, "Description must be at most 50 characters", HttpStatus.BAD_REQUEST),
    // Notification
    NOTIFICATIONNAME_BLANK(1000, "Name must be not blank", HttpStatus.BAD_REQUEST),
    NOTIFICATIONNAME_INVALID_SIZE(1001, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    NOTIFICATION_DESCRIPTION_BLANK(1002, "Description must be at most 50 characters", HttpStatus.BAD_REQUEST),
    // Image
    FILE_BLANK(1100, "File must be not blank", HttpStatus.BAD_REQUEST),
    // Cost
    COSTNAME_BLANK(1200, "Name must be not blank", HttpStatus.BAD_REQUEST),
    COSTNAME_INVALID_SIZE(1201, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    STARTCOST_BLANK(1202, "Start cost must be not blank", HttpStatus.BAD_REQUEST),
    // Category
    CATEGORYNAME_BLANK(1300, "Name must be not blank", HttpStatus.BAD_REQUEST),
    CATEGORYNAME_INVALID_SIZE(1301, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    CATEGORY_DESCRIPTION_BLANK(1302, "Description must be at most 50 characters", HttpStatus.BAD_REQUEST),
    // Benefit
    BENEFITNAME_BLANK(1400, "Name must be not blank", HttpStatus.BAD_REQUEST),
    BENEFITNAME_INVALID_SIZE(1401, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    BENEFIT_DESCRIPTION_BLANK(1402, "Description must be at most 50 characters", HttpStatus.BAD_REQUEST),
    // Auction
    AUCTIONNAME_BLANK(1500, "Name must be not blank", HttpStatus.BAD_REQUEST),
    AUCTIONNAME_INVALID_SIZE(1501, "Name must be at most 50 characters", HttpStatus.BAD_REQUEST),
    AUCTION_DESCRIPTION_BLANK(1502, "Description must be at most 50 characters", HttpStatus.BAD_REQUEST),
    STARTTIME_NOT_IN_FUTURE_OR_PRESENT(1503, "Star time must be in the future or present", HttpStatus.BAD_REQUEST),
    AUCTION_TIME_BLANK(1504, "Time must be not blank", HttpStatus.BAD_REQUEST),
    AUCTION_PRODUCT_BLANK(1505, "Product must be not blank", HttpStatus.BAD_REQUEST),
    AUCTION_COST_BLANK(1506, "Cost must be not blank", HttpStatus.BAD_REQUEST),
    AUCTION_STEP_BLANK(1507, "Step must be not blank", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
