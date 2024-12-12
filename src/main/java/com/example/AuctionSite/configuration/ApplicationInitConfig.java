package com.example.AuctionSite.configuration;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.AuctionSite.entity.*;
import com.example.AuctionSite.repository.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    TimeRepository timeRepository;
    StepRepository stepRepository;
    RateRepository rateRepository;
    CostRepository costRepository;
    CategoryRepository categoryRepository;
    NotificationRepository notificationRepository;
    StatusRepository statusRepository;
    RanksRepository ranksRepository;
    ImageRepository imageRepository;
    DeliveryTypeRepository deliveryTypeRepository;
    PaymentTypeRepository paymentTypeRepository;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            initializePermissions();
            initializeRoles();
            initializeTimes();
            initializeSteps();
            initializeRates();
            initializeCosts();
            initializeCategories();
            initializeNotifications();
            initializeStatus();
            initializeRanks();
            initializeImages();
            initializeDeliveryTypes();
            initializePaymentTypes();
            createDefaultAdminUser();
        };
    }

    private void initializePermissions() {
        // spotless:off
        createPermissionIfNotExists("GET_ALL_USERS", "Get all users permission");
        createPermissionIfNotExists("GET_USER_BY_ID", "Get user by id permission");
        createPermissionIfNotExists("GET_USER_BY_USERNAME", "Get user by username permission");
        createPermissionIfNotExists("UPDATE_USER", "Update user permission"); // USER
        createPermissionIfNotExists("DELETE_USER", "Delete user permission"); // USER
        createPermissionIfNotExists("GET_ALL_USERS_JOINED_BY_AUCTIONID_OF_USER", "Get all users joined by auctionid of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_USERS_JOINED_BY_AUCTIONID", "Get all users joined by auctionid of user permission");
        
        createPermissionIfNotExists("CREATE_TIME", "Create time permission");
        createPermissionIfNotExists("GET_ALL_TIMES", "Get all times permission");
        createPermissionIfNotExists("GET_TIME_BY_NAME", "Get time by name permission");
        createPermissionIfNotExists("UPDATE_TIME", "Update time permission");
        createPermissionIfNotExists("DELETE_TIME", "Delete time permission");

        createPermissionIfNotExists("CREATE_STEP", "Create step permission");
        createPermissionIfNotExists("GET_ALL_STEPS", "Get all steps permission");
        createPermissionIfNotExists("GET_STEP_BY_NAME", "Get step by name permission");
        createPermissionIfNotExists("UPDATE_STEP", "Update step permission");
        createPermissionIfNotExists("DELETE_STEP", "Delete step permission");

        createPermissionIfNotExists("CREATE_STATUS", "Create status permission");
        createPermissionIfNotExists("GET_ALL_STATUS", "Get all status permission");
        createPermissionIfNotExists("GET_STATUS_BY_NAME", "Get status by name permission");
        createPermissionIfNotExists("UPDATE_STATUS", "Update status permission");
        createPermissionIfNotExists("DELETE_STATUS", "Delete status permission");

        createPermissionIfNotExists("CREATE_ROLE", "Create role permission");
        createPermissionIfNotExists("GET_ALL_ROLES", "Get all roles permission");
        createPermissionIfNotExists("UPDATE_ROLE", "Update role permission");
        createPermissionIfNotExists("DELETE_ROLE", "Delete role permission");

        createPermissionIfNotExists("CREATE_RATE", "Create rate permission");
        createPermissionIfNotExists("GET_ALL_RATES", "Get all rates permission");
        createPermissionIfNotExists("GET_RATE_BY_NUMBEROFSTAR", "Get rate by number of star permission");
        createPermissionIfNotExists("UPDATE_RATE", "Update rate permission");
        createPermissionIfNotExists("DELETE_RATE", "Delete rate permission");

        createPermissionIfNotExists("CREATE_RANK", "Create rank permission");
        createPermissionIfNotExists("GET_ALL_RANKS", "Get all ranks permission");
        createPermissionIfNotExists("GET_RANK_BY_NAME", "Get rank by name permission");
        createPermissionIfNotExists("UPDATE_RANK", "Update rank permission");
        createPermissionIfNotExists("DELETE_RANK", "Delete rank permission");

        createPermissionIfNotExists("CREATE_PRODUCT", "Create product permission"); // USER
        createPermissionIfNotExists("GET_ALL_PRODUCTS", "Get all products permission");
        createPermissionIfNotExists("GET_ALL_PRODUCTS_BY_USERID", "Get all products permission");
        createPermissionIfNotExists("GET_ALL_PRODUCTS_OF_USER", "Get all products of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_PRODUCTS_PENDING_AUCTION_PAGED_OF_USER", "Get all products pending auction of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_PRODUCTS_ACTIVE_PAGED_OF_USER", "Get all products active of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_PRODUCTS_ADDED_PAGED_OF_USER", "Get all products added of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_PRODUCTS_SOLD_PAGED_OF_USER", "Get all products sold of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_PRODUCTS_SA_PAGED_OF_USER", "Get all products sold of user permission"); // USER
        createPermissionIfNotExists("GET_PRODUCT_BY_ID", "Get product by id permission");
        createPermissionIfNotExists("GET_PRODUCT_BY_ID_OF_USER", "Get product by id of user permission"); // USER
        createPermissionIfNotExists("GET_PRODUCT_BY_NAME", "Get product by name permission");
        createPermissionIfNotExists("GET_PRODUCT_BY_NAME_OF_USER", "Get product by name of user permission"); // USER
        createPermissionIfNotExists("UPDATE_PRODUCT", "Update product permission"); // USER
        createPermissionIfNotExists("DELETE_PRODUCT", "Delete product permission"); // USER

        createPermissionIfNotExists("CREATE_PERMISSION", "Create permission permission");
        createPermissionIfNotExists("GET_ALL_PERMISSIONS", "Get all permissions permission");
        createPermissionIfNotExists("UPDATE_PERMISSION", "Update permission permission");
        createPermissionIfNotExists("DELETE_PERMISSION", "Delete permission permission");

        createPermissionIfNotExists("CREATE_NOTIFICATION", "Create notification permission");
        createPermissionIfNotExists("GET_ALL_NOTIFICATIONS", "Get all notifications permission");
        createPermissionIfNotExists("GET_NOTIFICATION_BY_NAME", "Get notification by name permission");
        createPermissionIfNotExists("UPDATE_NOTIFICATION", "Update notification permission");
        createPermissionIfNotExists("DELETE_NOTIFICATION", "Delete notification permission");

        createPermissionIfNotExists("UPLOAD_IMAGES", "Upload images permission");
        createPermissionIfNotExists("GET_ALL_IMAGES", "Get all images permission");
        createPermissionIfNotExists("GET_IMAGE_BY_ID", "Get image by id permission");
        createPermissionIfNotExists("UPDATE_IMAGE", "Update image permission");
        createPermissionIfNotExists("DELETE_IMAGE", "Delete image permission");

        createPermissionIfNotExists("ADD_FOLLOW_AUCTION", "Add follow auction permission"); // USER
        createPermissionIfNotExists("UNFOLLOW_AUCTION", "Unfollow auction permission"); // USER
        createPermissionIfNotExists("GET_ALL_FOLLOW_BY_USERID", "Get all follows by userId permission");
        createPermissionIfNotExists("GET_ALL_FOLLOW_BY_AUCTIONID", "Get all follows by auctionId permission");
        createPermissionIfNotExists("GET_ALL_FOLLOW_OF_USER", "Get all follows of user permission"); // USER

        createPermissionIfNotExists("CREATE_COST", "Create cost permission");
        createPermissionIfNotExists("GET_ALL_COSTS", "Get all costs permission");
        createPermissionIfNotExists("GET_COST_BY_NAME", "Get cost by name permission");
        createPermissionIfNotExists("UPDATE_COST", "Update cost permission");
        createPermissionIfNotExists("DELETE_COST", "Delete cost permission");

        createPermissionIfNotExists("CREATE_CONTACT", "Create contact permission");
        createPermissionIfNotExists("GET_CONTACT_BY_ID", "Get contact by id permission");
        createPermissionIfNotExists("UPDATE_CONTACT", "Update contact permission");
        createPermissionIfNotExists("DELETE_CONTACT", "Delete contact permission");

        createPermissionIfNotExists("CREATE_CATEGORY", "Create category permission");
        createPermissionIfNotExists("GET_CATEGORY_BY_NAME", "Get category by name permission");
        createPermissionIfNotExists("UPDATE_CATEGORY", "Update category permission");
        createPermissionIfNotExists("DELETE_CATEGORY", "Delete category permission");
        
        createPermissionIfNotExists("CREATE_BID", "Create bid permission"); //USER
        createPermissionIfNotExists("GET_ALL_BIDS_BY_USERID", "Get all bids by userid permission");
        createPermissionIfNotExists("GET_ALL_BIDS_BY_AUCTIONID", "Get all bids by auctionid permission");
        createPermissionIfNotExists("GET_ALL_BIDS_OF_USER", "Get all bids of user permission"); //USER
        createPermissionIfNotExists("GET_ALL_BIDS_BY_AUCTIONID_OF_USER", "Get all bids by auctionid of user permission"); //USER

        createPermissionIfNotExists("CREATE_BENEFIT", "Create benefit permission");
        createPermissionIfNotExists("GET_ALL_BENEFITS", "Get all benefits permission");
        createPermissionIfNotExists("GET_BENEFIT_BY_NAME", "Get benefit by name permission");
        createPermissionIfNotExists("UPDATE_BENEFIT", "Update benefit permission");
        createPermissionIfNotExists("DELETE_BENEFIT", "Delete benefit permission");

        createPermissionIfNotExists("CREATE_AUCTION", "Create auction permission"); // USER
        createPermissionIfNotExists("GET_ALL_AUCTIONS", "Get all auctions permission");
        createPermissionIfNotExists("GET_ALL_AUCTIONS_BY_USERID", "Get all products permission");
        createPermissionIfNotExists("GET_ALL_AUCTIONS_OF_USER", "Get all auctions of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_AUCTIONS_OF_USER_JOINED", "Get all auctions of user joined permission"); // USER
        createPermissionIfNotExists("GET_ALL_AUCTIONS_PENDING_OF_USER", "Get all auctions pending of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_AUCTIONS_ONGOING_OF_USER", "Get all auctions ongoing of user permission"); // USER
        createPermissionIfNotExists("GET_ALL_AUCTIONS_ENDED_OF_USER", "Get all auctions ended of user permission"); // USER
        createPermissionIfNotExists("GET_AUCTION_BY_ID", "Get auction by id permission");
        createPermissionIfNotExists("GET_AUCTION_BY_ID_OF_USER", "Get auction by id of user permission"); // USER
        createPermissionIfNotExists("GET_AUCTION_BY_NAME", "Get auction by name permission");
        createPermissionIfNotExists("GET_AUCTION_BY_NAME_OF_USER", "Get auction by name of user permission"); // USER
        createPermissionIfNotExists("UPDATE_AUCTION", "Update auction permission"); // USER
        createPermissionIfNotExists("DELETE_AUCTION", "Delete auction permission"); // USER
        
        createPermissionIfNotExists("CREATE_DELIVERYTYPE", "Create delivery type permission");
        createPermissionIfNotExists("GET_ALL_DELIVERYTYPES", "Get all delivery types permission");
        createPermissionIfNotExists("GET_DELIVERYTYPE_BY_NAME", "Get delivery type by name permission");
        createPermissionIfNotExists("UPDATE_DELIVERYTYPE", "Update delivery type permission");
        createPermissionIfNotExists("DELETE_DELIVERYTYPE", "Deleted delivery type permission");
        
        createPermissionIfNotExists("CREATE_PAYMENTTYPE", "Create payment type permission");
        createPermissionIfNotExists("GET_ALL_PAYMENTTYPES", "Get all payment types permission");
        createPermissionIfNotExists("GET_PAYMENTTYPE_BY_NAME", "Get payment type by name permission");
        createPermissionIfNotExists("UPDATE_PAYMENTTYPE", "Update payment type permission");
        createPermissionIfNotExists("DELETE_PAYMENTTYPE", "Delete payment type permission");
        
        createPermissionIfNotExists("CREATE_REGULATION", "Create regulation permission");
        createPermissionIfNotExists("GET_REGULATION_BY_NAME", "Get regulation by name permission");
        createPermissionIfNotExists("UPDATE_REGULATION", "Update regulation permission");
        createPermissionIfNotExists("DELETE_REGULATION", "Delete regulation permission");
        
        createPermissionIfNotExists("GET_ALL_RECEIPTS", "Get all receipts permission");
        createPermissionIfNotExists("GET_RECEIPT_BY_ID", "Get receipt by id permission");
        createPermissionIfNotExists("GET_RECEIPT_BY_PRODUCTID", "Get receipt by product id permission");
        createPermissionIfNotExists("GET_RECEIPTS_BY_SELLERID", "Get receipts by seller id permission");
        createPermissionIfNotExists("GET_RECEIPTS_BY_BUYERID", "Get receipts by buyer id permission");
        createPermissionIfNotExists("GET_RECEIPTS_OF_SELLER", "Get receipts of seller permission"); //USER
        createPermissionIfNotExists("GET_RECEIPTS_OF_BUYER", "Get receipts of buyer permission"); //USER
        createPermissionIfNotExists("GET_RECEIPT_BY_ID_OF_BUYER", "Get receipt by id of buyer permission"); //USER
        createPermissionIfNotExists("GET_RECEIPT_BY_ID_OF_SELLER", "Get receipt by id of seller permission"); //USER
        createPermissionIfNotExists("UPDATE_RECEIPT", "Update receipt permission"); //USER
        createPermissionIfNotExists("DELETE_RECEIPT", "Delete receipt permission"); //USER
        createPermissionIfNotExists("PAYMENT_CONFIRM_SUCCESS", "Payment confirm success permission"); //USER
        createPermissionIfNotExists("PAYMENT_CONFIRM_FAILURE", "Payment confirm failure permission"); //USER
        // spotless:on
    }

    private void initializeRoles() {
        createRoleIfNotExists("ADMIN", "ADMIN role");
        createRoleIfNotExists("USER", "USER role");
    }

    private void initializeTimes() {
        createTimeIfNotExists("5m", Duration.ofMinutes(5));
        createTimeIfNotExists("2m", Duration.ofMinutes(2));

        createTimeIfNotExists("1h", Duration.ofHours(1));
        createTimeIfNotExists("2h", Duration.ofHours(2));
        createTimeIfNotExists("3h", Duration.ofHours(3));
        createTimeIfNotExists("4h", Duration.ofHours(4));
        createTimeIfNotExists("5h", Duration.ofHours(5));
        createTimeIfNotExists("6h", Duration.ofHours(6));
        createTimeIfNotExists("7h", Duration.ofHours(7));
        createTimeIfNotExists("8h", Duration.ofHours(8));
        createTimeIfNotExists("9h", Duration.ofHours(9));
        createTimeIfNotExists("11h", Duration.ofHours(11));
        createTimeIfNotExists("10h", Duration.ofHours(10));
        createTimeIfNotExists("12h", Duration.ofHours(12));
        createTimeIfNotExists("13h", Duration.ofHours(13));
        createTimeIfNotExists("14h", Duration.ofHours(14));
        createTimeIfNotExists("15h", Duration.ofHours(15));
        createTimeIfNotExists("16h", Duration.ofHours(16));
        createTimeIfNotExists("17h", Duration.ofHours(17));
        createTimeIfNotExists("18h", Duration.ofHours(18));
        createTimeIfNotExists("19h", Duration.ofHours(19));
        createTimeIfNotExists("20h", Duration.ofHours(20));
        createTimeIfNotExists("21h", Duration.ofHours(21));
        createTimeIfNotExists("22h", Duration.ofHours(22));
        createTimeIfNotExists("23h", Duration.ofHours(23));

        createTimeIfNotExists("1d", Duration.ofDays(1));
        createTimeIfNotExists("2d", Duration.ofDays(2));
        createTimeIfNotExists("3d", Duration.ofDays(3));
        createTimeIfNotExists("4d", Duration.ofDays(4));
        createTimeIfNotExists("5d", Duration.ofDays(5));
        createTimeIfNotExists("6d", Duration.ofDays(6));
        createTimeIfNotExists("7d", Duration.ofDays(7));

        createTimeIfNotExists("1w", Duration.ofDays(7));
        createTimeIfNotExists("2w", Duration.ofDays(14));
        createTimeIfNotExists("3w", Duration.ofDays(21));
        createTimeIfNotExists("4w", Duration.ofDays(28));
    }

    private void initializeSteps() {
        createStepIfNotExists("1M", BigDecimal.valueOf(1_000_000));
        createStepIfNotExists("2M", BigDecimal.valueOf(2_000_000));
        createStepIfNotExists("3M", BigDecimal.valueOf(3_000_000));
        createStepIfNotExists("4M", BigDecimal.valueOf(4_000_000));
        createStepIfNotExists("5M", BigDecimal.valueOf(5_000_000));
        createStepIfNotExists("6M", BigDecimal.valueOf(6_000_000));
        createStepIfNotExists("7M", BigDecimal.valueOf(7_000_000));
        createStepIfNotExists("8M", BigDecimal.valueOf(8_000_000));
        createStepIfNotExists("9M", BigDecimal.valueOf(9_000_000));

        createStepIfNotExists("10M", BigDecimal.valueOf(10_000_000));
        createStepIfNotExists("20M", BigDecimal.valueOf(20_000_000));
        createStepIfNotExists("30M", BigDecimal.valueOf(30_000_000));
        createStepIfNotExists("40M", BigDecimal.valueOf(40_000_000));
        createStepIfNotExists("50M", BigDecimal.valueOf(50_000_000));
    }

    private void initializeRates() {
        createRateIfNotExists(0F, "Undetermined");
        createRateIfNotExists(0.5F, "Very poor");
        createRateIfNotExists(1F, "Least");
        createRateIfNotExists(1.5F, "Medium");
        createRateIfNotExists(2F, "Good");
        createRateIfNotExists(2.5F, "Very good");
        createRateIfNotExists(3F, "Excellent");
        createRateIfNotExists(3.5F, "Great");
        createRateIfNotExists(4F, "Outstanding");
        createRateIfNotExists(4.5F, "Extremely Excellent");
        createRateIfNotExists(5F, "Perfect");
    }

    private void initializeCosts() {
        createCostIfNotExists("1M", BigDecimal.valueOf(1_000_000));
        createCostIfNotExists("2M", BigDecimal.valueOf(2_000_000));
        createCostIfNotExists("3M", BigDecimal.valueOf(3_000_000));
        createCostIfNotExists("4M", BigDecimal.valueOf(4_000_000));
        createCostIfNotExists("5M", BigDecimal.valueOf(5_000_000));
        createCostIfNotExists("6M", BigDecimal.valueOf(6_000_000));
        createCostIfNotExists("7M", BigDecimal.valueOf(7_000_000));
        createCostIfNotExists("8M", BigDecimal.valueOf(8_000_000));
        createCostIfNotExists("9M", BigDecimal.valueOf(9_000_000));

        createCostIfNotExists("10M", BigDecimal.valueOf(10_000_000));
        createCostIfNotExists("20M", BigDecimal.valueOf(20_000_000));
        createCostIfNotExists("30M", BigDecimal.valueOf(30_000_000));
        createCostIfNotExists("40M", BigDecimal.valueOf(40_000_000));
        createCostIfNotExists("50M", BigDecimal.valueOf(50_000_000));
        createCostIfNotExists("60M", BigDecimal.valueOf(60_000_000));
        createCostIfNotExists("70M", BigDecimal.valueOf(70_000_000));
        createCostIfNotExists("80M", BigDecimal.valueOf(80_000_000));
        createCostIfNotExists("90M", BigDecimal.valueOf(90_000_000));
        createCostIfNotExists("100M", BigDecimal.valueOf(100_000_000));
    }

    private void initializeCategories() {
        createCategoryIfNotExists("DDT", "Đồ điện tử");
        createCategoryIfNotExists("TS&DH", "Trang sức và đồng hồ");
        createCategoryIfNotExists("TT", "Thời trang");
        createCategoryIfNotExists("TPNT", "Tác phẩm nghệ thuật");
        createCategoryIfNotExists("STCV", "Sưu tập cổ vật");
        createCategoryIfNotExists("DNT&TT", "Đồ nội thất và trang trí");
        createCategoryIfNotExists("XC", "Xe cộ");
        createCategoryIfNotExists("DTT&DCGT", "Đồ thể thao và dụng cụ giải trí");
        createCategoryIfNotExists("BDS", "Bất động sản");
        createCategoryIfNotExists("DGD", "Đồ gia dụng");
    }

    private void initializeNotifications() {
        // spotless:off
        //User
        createNotificationIfNotExists("PROMOTIONAL_NOTICE", "Khuyến mãi đặc biệt! Nhập mã khuyến mãi để nhận ưu đãi khi tham gia đấu giá. Thời gian có hạn, đừng bỏ lỡ!");
        createNotificationIfNotExists("REGISTRATION_SUCCESSFUL_NOTIFICATION", "Đăng ký thành công! Vui lòng kiểm tra email để xác thực tài khoản và bắt đầu trải nghiệm.");
        createNotificationIfNotExists("ACCOUNT_VERIFICATION_NOTICE", "Vui lòng xác thực tài khoản qua liên kết trong email để hoàn tất quá trình đăng ký.");
        createNotificationIfNotExists("SUCCESSFUL_LOGIN_NOTIFICATION_AGAIN", "Bạn đã đăng nhập thành công. Chào mừng bạn quay trở lại!");
        createNotificationIfNotExists("SUCCESSFUL_LOGIN_NOTIFICATION", "Bạn đã đăng nhập thành công!");
        createNotificationIfNotExists("LOGIN_FAILED_MESSAGE", "Đăng nhập không thành công. Vui lòng kiểm tra lại thông tin hoặc xác thực tài khoản.");
        createNotificationIfNotExists("FORGTO_PASSWORD_NOTICE", "Yêu cầu khôi phục mật khẩu thành công! Vui lòng kiểm tra email của bạn để đặt lại mật khẩu.");
        createNotificationIfNotExists("PASSWORD_CHANGE_SUCCESSFUL_NOTIFICATION", "Mật khẩu của bạn đã được thay đổi thành công. Vui lòng sử dụng mật khẩu mới để đăng nhập.");
        createNotificationIfNotExists("LOGOUT_NOTICE", "Bạn đã đăng xuất thành công. Hẹn gặp lại!");
        createNotificationIfNotExists("PERSONAL_INFORMATION_UPDATE_NOTICE", "Thông tin cá nhân của bạn đã được cập nhật thành công.");
        //Auction
        createNotificationIfNotExists("NEW_AUCTION_NOTICE", "Phiên đấu giá mới đã bắt đầu! Đừng bỏ lỡ cơ hội sở hữu sản phẩm này.");
        createNotificationIfNotExists("AUCTION_END_NOTICE", "Phiên đấu giá đã kết thúc.");
        createNotificationIfNotExists("CONGRATULATION_NOTICE", "Xin chúc mừng! Bạn đã thắng phiên đấu giá.");
        createNotificationIfNotExists("DELIVERY_NOTICE", "Đơn hàng của bạn đang được xử lý và sẽ sớm được giao. Cảm ơn bạn đã tham gia đấu giá!");
        //Bid
        createNotificationIfNotExists("HIGHEST_BID_NOTICE", "Bạn đang là người đặt giá cao nhất. Tiếp tục theo dõi để không bị vượt qua!");
        createNotificationIfNotExists("OVERPRICED_NOTICE", "Giá bạn đã đặt đã bị vượt qua. Đặt giá cao hơn để tiếp tục tham gia phiên đấu giá.");
        createNotificationIfNotExists("BID_CONFIRMATION_NOTICE", "Giá của bạn đã được xác nhận thành công.");
        createNotificationIfNotExists("NOTICE_OF_VIOLATION", "Bạn đã vi phạm quy định đấu giá. Vui lòng tuân thủ quy định để tránh bị khóa tài khoản.");
        //Payment
        createNotificationIfNotExists("PAYMENT_NOTICE", "Vui lòng hoàn tất thanh toán để nhận hàng.");
        //spotless:on
    }

    private void initializeStatus() {
        // User
        createStatusIfNotExists("ONLINE", "Đang hoạt động");
        createStatusIfNotExists("OFFLINE", "Đang không hoạt động");
        createStatusIfNotExists("REGISTERED", "Đang đăng ký");
        createStatusIfNotExists("LOGGED_IN", "Đã đăng nhập");
        createStatusIfNotExists("LOGGED_OUT", "Đã đăng xuất");
        createStatusIfNotExists("PARTICIPATE_IN_AUCTION", "Đang tham gia đấu giá");
        // Product
        createStatusIfNotExists("PENDING_APPROVAL", "Chờ duyệt");
        createStatusIfNotExists("PENDING_AUCTION", "Chờ đấu giá");
        createStatusIfNotExists("ADDED", "Đã thêm");
        createStatusIfNotExists("ACTIVE", "Đang đấu giá");
        createStatusIfNotExists("SOLD", "Đã bán");
        createStatusIfNotExists("PENDING_SOLD", "Chờ bán");
        createStatusIfNotExists("UNSOLD", "Không bán được");
        createStatusIfNotExists("SUSPENDED", "Tạm ngưng");
        createStatusIfNotExists("ON_DELIVERY", "Đang giao hàng");
        // Auction
        createStatusIfNotExists("PENDING", "Chờ bắt đầu");
        createStatusIfNotExists("ONGOING", "Đang diễn ra");
        createStatusIfNotExists("CLOSING_SOON", "Sắp kết thúc");
        createStatusIfNotExists("ENDED", "Đã kết thúc");
        createStatusIfNotExists("COMPLETED", "Đã hoàn tất");
        createStatusIfNotExists("CANCELLED", "Hủy đấu giá");
        // Payment
        createStatusIfNotExists("WAITING_FOR_PAYMENT", "Chờ thanh toán");
        createStatusIfNotExists("PAID", "Đã thanh toán");
        createStatusIfNotExists("CANCELLED", "Đã hủy thanh toán");
    }

    private void initializeRanks() {
        createRankIfNotExists("BRONZE", "Hạng Đồng", 0, Duration.ZERO, 1);
        createRankIfNotExists("SILVER", "Hạng Bạc", 1, Duration.ofMinutes(1), 2);
        createRankIfNotExists("GOLD", "Hạng Vàng", 2, Duration.ofMinutes(2), 3);
        createRankIfNotExists("PLATINUM", "Hạng Bạch Kim", 3, Duration.ofMinutes(3), 4);
        createRankIfNotExists("DIAMOND", "Hạng Kim Cương", 4, Duration.ofMinutes(4), 5);
    }

    private void initializeImages() {
        createImagesIfNotExists(1, "/auctionsite/image_default/auction_image.jpg");
    }

    private void initializeDeliveryTypes() {
        // spotless:off
        createDeliveryTypesIfNotExists("STANDARD_DELIVERY", "Phương thức cơ bản, chi phí thấp, thời gian từ vài ngày đến một tuần.");
        createDeliveryTypesIfNotExists("FAST_DELIVERY", "Thời gian ngắn (1-3 ngày), chi phí cao hơn giao hàng tiêu chuẩn.");
        createDeliveryTypesIfNotExists("SAME_DAY_DELIVERY", "Nhận hàng ngay trong ngày đặt, chi phí cao nhất.");
        createDeliveryTypesIfNotExists("THIRD_PARTY_DELIVERY", "Người mua chọn đơn vị vận chuyển bên thứ 3, linh hoạt về dịch vụ và chi phí.");
        createDeliveryTypesIfNotExists("PICK_UP_AT_STORE/WAREHOUSE", "Người mua đến nhận hàng trực tiếp, tiết kiệm chi phí.");
        createDeliveryTypesIfNotExists("INTERNATIONAL_DELIVERY", "Gửi hàng đến quốc gia khác, thời gian và chi phí tùy thuộc vào địa điểm.");
        //spotless:on
    }

    private void initializePaymentTypes() {
        // spotless:off
        createPaymentTypesIfNotExists("PAYMENT_BY_CREDIT/DEBIT_CARD", "Hỗ trợ Visa, Mastercard, giao dịch nhanh chóng, an toàn.");
        createPaymentTypesIfNotExists("PAYMENT_VIA_E-WALLET", "Ví như PayPal, ZaloPay, Momo, thuận tiện, không cần nhập thông tin thẻ.");
        createPaymentTypesIfNotExists("BANK_TRANSFER", "Chuyển khoản trực tiếp, an toàn cho giao dịch lớn.");
        createPaymentTypesIfNotExists("INSTALLMENT_PAYMENT", "Quét mã QR qua ứng dụng ngân hàng hoặc ví điện tử.");
        createPaymentTypesIfNotExists("COD_PAYMENT", "Thu tiền khi nhận hàng, phù hợp khi cần kiểm tra hàng trước.");
        //spotless:on
    }

    private void createDefaultAdminUser() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            Set<Role> role = Set.of(roleRepository.findById("ADMIN").orElseThrow());
            User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(role)
                    .build();
            userRepository.save(user);
            log.warn("admin user has been created with default username & password: admin");
        }
    }

    private void createTimeIfNotExists(String timeName, Duration timeDuration) {
        timeRepository.findById(timeName).ifPresentOrElse(time -> {}, () -> {
            Time time = Time.builder().name(timeName).time(timeDuration).build();
            timeRepository.save(time);
        });
    }

    private void createStepIfNotExists(String stepName, BigDecimal stepStep) {
        stepRepository.findById(stepName).ifPresentOrElse(step -> {}, () -> {
            Step step = Step.builder().name(stepName).step(stepStep).build();
            stepRepository.save(step);
        });
    }

    private void createRoleIfNotExists(String roleName, String description) {
        roleRepository.findById(roleName).ifPresentOrElse(role -> {}, () -> {
            Role role = Role.builder().name(roleName).description(description).build();
            if ("ADMIN".equals(roleName)) {
                HashSet<Permission> permissions = new HashSet<>(permissionRepository.findAll());
                role.setPermissions(permissions);
            } else if ("USER".equals(roleName)) {
                // spotless:off
                HashSet<Permission> permissions = new HashSet<>();
                
                permissions.add(permissionRepository.findById("UPDATE_USER").orElseThrow());
                permissions.add(permissionRepository.findById("DELETE_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_USERS_JOINED_BY_AUCTIONID_OF_USER").orElseThrow());

                permissions.add(permissionRepository.findById("CREATE_PRODUCT").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_PRODUCTS_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_PRODUCTS_PENDING_AUCTION_PAGED_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_PRODUCTS_ACTIVE_PAGED_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_PRODUCTS_ADDED_PAGED_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_PRODUCTS_SOLD_PAGED_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_PRODUCTS_SA_PAGED_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_PRODUCT_BY_ID_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_PRODUCT_BY_NAME_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("UPDATE_PRODUCT").orElseThrow());
                permissions.add(permissionRepository.findById("DELETE_PRODUCT").orElseThrow());

                permissions.add(permissionRepository.findById("CREATE_AUCTION").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_AUCTIONS_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_AUCTIONS_OF_USER_JOINED").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_AUCTIONS_PENDING_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_AUCTIONS_ONGOING_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_AUCTIONS_ENDED_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_AUCTION_BY_ID_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_AUCTION_BY_NAME_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("UPDATE_AUCTION").orElseThrow());
                permissions.add(permissionRepository.findById("DELETE_AUCTION").orElseThrow());

                permissions.add(permissionRepository.findById("ADD_FOLLOW_AUCTION").orElseThrow());
                permissions.add(permissionRepository.findById("UNFOLLOW_AUCTION").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_FOLLOW_OF_USER").orElseThrow());
                
                permissions.add(permissionRepository.findById("CREATE_BID").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_BIDS_OF_USER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_ALL_BIDS_BY_AUCTIONID_OF_USER").orElseThrow());
                
                permissions.add(permissionRepository.findById("GET_RECEIPTS_OF_SELLER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_RECEIPTS_OF_BUYER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_RECEIPT_BY_ID_OF_BUYER").orElseThrow());
                permissions.add(permissionRepository.findById("GET_RECEIPT_BY_ID_OF_SELLER").orElseThrow());
                permissions.add(permissionRepository.findById("UPDATE_RECEIPT").orElseThrow());
                permissions.add(permissionRepository.findById("DELETE_RECEIPT").orElseThrow());
                permissions.add(permissionRepository.findById("PAYMENT_CONFIRM_SUCCESS").orElseThrow());
                permissions.add(permissionRepository.findById("PAYMENT_CONFIRM_FAILURE").orElseThrow());
                
                role.setPermissions(permissions);
                //spotless:on
            }
            roleRepository.save(role);
        });
    }

    private void createRateIfNotExists(Float numberOfStar, String description) {
        rateRepository.findById(numberOfStar).ifPresentOrElse(rate -> {}, () -> {
            Rate rate = Rate.builder()
                    .numberOfStar(numberOfStar)
                    .description(description)
                    .build();
            rateRepository.save(rate);
        });
    }

    private void createPermissionIfNotExists(String permissionName, String description) {
        permissionRepository.findById(permissionName).ifPresentOrElse(permission -> {}, () -> {
            Permission permission = Permission.builder()
                    .name(permissionName)
                    .description(description)
                    .build();
            permissionRepository.save(permission);
        });
    }

    private void createCostIfNotExists(String name, BigDecimal startCost) {
        costRepository.findById(name).ifPresentOrElse(cost -> {}, () -> {
            Cost cost = Cost.builder().name(name).startCost(startCost).build();
            costRepository.save(cost);
        });
    }

    private void createCategoryIfNotExists(String name, String description) {
        categoryRepository.findById(name).ifPresentOrElse(category -> {}, () -> {
            Category category =
                    Category.builder().name(name).description(description).build();
            categoryRepository.save(category);
        });
    }

    private void createNotificationIfNotExists(String name, String description) {
        notificationRepository.findById(name).ifPresentOrElse(notification -> {}, () -> {
            Notification notification =
                    Notification.builder().name(name).description(description).build();
            notificationRepository.save(notification);
        });
    }

    private void createStatusIfNotExists(String name, String description) {
        statusRepository.findById(name).ifPresentOrElse(status -> {}, () -> {
            Status status = Status.builder().name(name).description(description).build();
            statusRepository.save(status);
        });
    }

    private void createRankIfNotExists(
            String name,
            String description,
            Integer successfulTransactions,
            Duration membershipDuration,
            Integer activityFrequency) {
        ranksRepository.findById(name).ifPresentOrElse(ranks -> {}, () -> {
            Ranks ranks = Ranks.builder()
                    .name(name)
                    .description(description)
                    .successfulTransactions(successfulTransactions)
                    .membershipDuration(membershipDuration)
                    .activityFrequency(activityFrequency)
                    .build();
            ranksRepository.save(ranks);
        });
    }

    private void createImagesIfNotExists(Integer id, String imageURL) {
        imageRepository.findById(id).ifPresentOrElse(image -> {}, () -> {
            Image image = Image.builder().id(id).imageURL(imageURL).build();
            imageRepository.save(image);
        });
    }

    private void createDeliveryTypesIfNotExists(String name, String description) {
        deliveryTypeRepository.findById(name).ifPresentOrElse(deliveryType -> {}, () -> {
            DeliveryType deliveryType =
                    DeliveryType.builder().name(name).description(description).build();
            deliveryTypeRepository.save(deliveryType);
        });
    }

    private void createPaymentTypesIfNotExists(String name, String description) {
        paymentTypeRepository.findById(name).ifPresentOrElse(paymentType -> {}, () -> {
            PaymentType paymentType =
                    PaymentType.builder().name(name).description(description).build();
            paymentTypeRepository.save(paymentType);
        });
    }
}
