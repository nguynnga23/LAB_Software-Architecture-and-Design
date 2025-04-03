# Microservices Order System

## Mô tả
Hệ thống **Microservices Order System** là một ứng dụng được xây dựng theo kiến trúc microservices, bao gồm các dịch vụ độc lập như `User Service`, `Order Service`, `Inventory Service`, `Customer Service`, `Eureka Server`, và `API Gateway`. Mỗi dịch vụ đảm nhận một chức năng cụ thể và giao tiếp với nhau thông qua REST API.

## Kiến trúc
Hệ thống bao gồm các thành phần chính:
- **Eureka Server**: Dịch vụ đăng ký và khám phá các microservices.
- **API Gateway**: Cổng giao tiếp chính, định tuyến các yêu cầu đến các dịch vụ tương ứng.
- **User Service**: Quản lý thông tin người dùng.
- **Order Service**: Quản lý đơn hàng.
- **Inventory Service**: Quản lý kho hàng.
- **Customer Service**: Tích hợp thông tin người dùng và đơn hàng.

## Các dịch vụ

### 1. User Service
- **Endpoint**: `/users`
- **Chức năng**:
  - Lấy thông tin người dùng theo ID.
- **Ví dụ API**:
  ```http
  GET /users/{id}
  ```

### 2. Order Service
- **Endpoint**: `/orders`
- **Chức năng**:
  - Lấy danh sách đơn hàng.
  - Lấy thông tin đơn hàng theo ID.
  - Tạo mới đơn hàng.
  - Xóa đơn hàng.
  - Lấy danh sách đơn hàng theo ID người dùng.
- **Ví dụ API**:
  ```http
  GET /orders
  GET /orders/{id}
  POST /orders
  DELETE /orders/{id}
  GET /orders/user/{userId}
  ```

### 3. Inventory Service
- **Endpoint**: `/api/inventory`
- **Chức năng**:
  - Lấy danh sách sản phẩm trong kho.
  - Lấy thông tin sản phẩm theo ID.
  - Thêm sản phẩm mới.
  - Xóa sản phẩm.
- **Ví dụ API**:
  ```http
  GET /api/inventory
  GET /api/inventory/{id}
  POST /api/inventory
  DELETE /api/inventory/{id}
  ```

### 4. Customer Service
- **Endpoint**: `/customers`
- **Chức năng**:
  - Lấy thông tin người dùng và danh sách đơn hàng của họ.
- **Ví dụ API**:
  ```http
  GET /customers/user/{id}/details
  ```

### 5. Eureka Server
- **Chức năng**:
  - Quản lý đăng ký và khám phá các dịch vụ.

### 6. API Gateway
- **Chức năng**:
  - Định tuyến các yêu cầu đến các dịch vụ tương ứng.
  - Cấu hình trong `application.yml`.

## Công nghệ sử dụng
- **Java 17**
- **Spring Boot**
- **Spring Cloud Netflix Eureka**
- **Spring Cloud Gateway**
- **Gradle**

## Cách chạy hệ thống
1. Khởi động **Eureka Server**.
2. Khởi động các dịch vụ: `User Service`, `Order Service`, `Inventory Service`, `Customer Service`.
3. Khởi động **API Gateway**.
4. Truy cập API thông qua cổng `8080` (API Gateway).

## Cấu hình
### API Gateway (`application.yml`):
```yaml
server:
  port: 8080

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: order-service
          uri: lb://order-service
          predicates:
            - Path=/api/orders/**
        - id: inventory-service
          uri: lb://inventory-service
          predicates:
            - Path=/api/inventory/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Đóng góp
Nếu bạn muốn đóng góp vào dự án, vui lòng tạo một pull request hoặc mở issue để thảo luận.

## Giấy phép
Dự án này được phát hành dưới giấy phép MIT.
