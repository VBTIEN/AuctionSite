# 🏛️ AuctionSite - Online Auction Platform

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> Một nền tảng đấu giá trực tuyến hiện đại được xây dựng với Spring Boot, tích hợp OAuth2 và nhiều tính năng nâng cao.

## ✨ Tính năng chính

- 🔐 **Xác thực & Phân quyền**: OAuth2 + JWT với Spring Security
- 🏷️ **Quản lý đấu giá**: Tạo, quản lý và tham gia đấu giá
- 📱 **QR Code**: Tạo và quét mã QR cho sản phẩm
- 📁 **Upload File**: Hỗ trợ upload hình ảnh (tối đa 10MB)
- 🔍 **Tìm kiếm thông minh**: Fuzzy search với FuzzyWuzzy
- 🌐 **Responsive UI**: Giao diện thân thiện với Thymeleaf
- ⏰ **Timezone Support**: Hỗ trợ múi giờ Việt Nam

## 🛠️ Công nghệ sử dụng

### Backend
- **Framework**: Spring Boot 3.3.4
- **Language**: Java 21
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA + Hibernate
- **Security**: Spring Security + OAuth2 + JWT
- **Build Tool**: Maven

### Frontend
- **Template Engine**: Thymeleaf
- **Security Integration**: Thymeleaf Spring Security

### Libraries & Tools
- **Lombok**: Giảm boilerplate code
- **MapStruct**: Object mapping
- **Jackson**: JSON processing
- **ZXing**: QR Code generation
- **FuzzyWuzzy**: Fuzzy string matching
- **Spotless**: Code formatting

## 🚀 Cài đặt và Chạy

### Yêu cầu hệ thống
- Java 21+
- MySQL 8.0+
- Maven 3.6+

### 1. Clone repository
```bash
git clone https://github.com/VBTIEN/AuctionSite.git
cd AuctionSite
```

### 2. Cấu hình Database
```sql
CREATE DATABASE auctionsite;
```

### 3. Cấu hình application.yml
```yaml
spring:
  datasource:
    url: "jdbc:mysql://localhost:3306/auctionsite"
    username: your_username
    password: your_password
```

### 4. Chạy ứng dụng
```bash
# Sử dụng Maven wrapper
./mvnw spring-boot:run

# Hoặc với Maven đã cài đặt
mvn spring-boot:run
```

### 5. Truy cập ứng dụng
```
http://localhost:8080/auctionsite
```

## 📁 Cấu trúc Project

```
src/
├── main/
│   ├── java/com/example/AuctionSite/
│   │   ├── configuration/     # Cấu hình Spring
│   │   ├── controller/        # REST Controllers
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── entity/           # JPA Entities
│   │   ├── exception/        # Exception Handlers
│   │   ├── mapper/           # MapStruct Mappers
│   │   ├── repository/       # JPA Repositories
│   │   └── service/          # Business Logic
│   └── resources/
│       ├── static/           # Static resources
│       └── application.yml   # Configuration
└── test/                     # Unit & Integration Tests
```

## 🔧 API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập
- `POST /api/auth/register` - Đăng ký
- `POST /api/auth/refresh` - Refresh token

### Auctions
- `GET /api/auctions` - Danh sách đấu giá
- `POST /api/auctions` - Tạo đấu giá mới
- `GET /api/auctions/{id}` - Chi tiết đấu giá
- `PUT /api/auctions/{id}` - Cập nhật đấu giá

### Bidding
- `POST /api/bids` - Đặt giá
- `GET /api/bids/auction/{id}` - Lịch sử đặt giá

## 🔐 Bảo mật

- **JWT Authentication**: Token-based authentication
- **OAuth2**: Hỗ trợ đăng nhập qua third-party
- **Password Encryption**: BCrypt hashing
- **CORS**: Cấu hình Cross-Origin Resource Sharing
- **Input Validation**: Bean Validation với Hibernate Validator

## 🧪 Testing

```bash
# Chạy tất cả tests
./mvnw test

# Chạy tests với coverage
./mvnw test jacoco:report
```

## 📝 Code Quality

```bash
# Format code với Spotless
./mvnw spotless:apply

# Check code formatting
./mvnw spotless:check
```

## 🌟 Tính năng nâng cao

### QR Code Integration
- Tự động tạo QR code cho mỗi sản phẩm đấu giá
- Quét QR để truy cập nhanh thông tin sản phẩm

### Fuzzy Search
- Tìm kiếm thông minh với khả năng chịu lỗi chính tả
- Hỗ trợ tìm kiếm tiếng Việt có dấu

### File Upload
- Upload multiple files
- Validation kích thước và định dạng
- Tối ưu hóa storage

## 🤝 Đóng góp

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

## 👨‍💻 Tác giả

**Vũ Bá Tiến** - [@VBTIEN](https://github.com/VBTIEN)

## 📞 Liên hệ

- Email: your.email@example.com
- GitHub: [@VBTIEN](https://github.com/VBTIEN)
- LinkedIn: [Your LinkedIn](https://linkedin.com/in/yourprofile)

---

⭐ **Nếu project này hữu ích, hãy cho một star nhé!** ⭐
