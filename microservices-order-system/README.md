# 🧱 Microservices E-commerce System

## 🗂️ Tổng quan

Hệ thống gồm 3 microservices chính được thiết kế theo kiến trúc Microservices, tuân thủ nguyên lý **Database per Service** và giao tiếp thông qua **REST/gRPC**, **Message Broker**, và **API Gateway**.

### 📦 Các dịch vụ chính

| Service          | Mô tả                                                                 |
|------------------|----------------------------------------------------------------------|
| Product Service  | Quản lý thông tin sản phẩm (tên, giá, mô tả, tồn kho,...)            |
| Order Service    | Quản lý đơn hàng (tạo, xem, hủy đơn hàng, gửi sự kiện đến broker)    |
| Customer Service | Quản lý thông tin khách hàng (tên, địa chỉ, email, số điện thoại,...)|

---

## 🔁 Giao tiếp giữa các Microservices

- **Client** gửi yêu cầu đến **API Gateway**.
- **API Gateway** định tuyến đến các service nội bộ qua REST/gRPC.
- **Order Service** gửi message bất đồng bộ thông qua **Kafka/RabbitMQ** (ví dụ khi tạo đơn hàng).
- Các service có thể gọi nhau trực tiếp khi cần kiểm tra trạng thái hoặc thông tin (VD: Order → Product).

---

## 🖼️ Kiến trúc hệ thống

```
                        +----------------------+
                        |      Client (UI)     |
                        +----------+-----------+
                                   |
                                   v
                     +-------------+--------------+
                     |         API Gateway        |
                     +------+------+------+-------+
                            /         |         \
                           v          v          v
              +------------+--+  +----+------+  +-------------+
              | Product Service|  |Order Service|  |Customer Service|
              +--+-------------+  +-----+------+  +------+------+
                 |                        |                |
                 v                        v                v
         Product DB (PostgreSQL)  Order DB (PostgreSQL)  Customer DB (MongoDB)

        Order Service cũng:
        - Gọi Product Service để kiểm tra tồn kho
        - Gửi message tới Message Broker để xử lý hậu cần, tồn kho, v.v.
```

---

## ⚙️ Docker & Docker Compose

### 📁 Cấu trúc thư mục gợi ý

```
.
├── api-gateway/
├── customer-service/
├── order-service/
├── product-service/
├── docker-compose.yml
└── README.md
```

### 🐳 docker-compose.yml (rút gọn)

```yaml
version: "3.8"
services:
  product-service:
    build: ./product-service
    ports:
      - "8081:8081"
    environment:
      - DB_URL=jdbc:postgresql://product-db:5432/productdb
    depends_on:
      - product-db

  order-service:
    build: ./order-service
    ports:
      - "8082:8082"
    environment:
      - DB_URL=jdbc:postgresql://order-db:5432/orderdb
    depends_on:
      - order-db

  customer-service:
    build: ./customer-service
    ports:
      - "8083:8083"
    environment:
      - DB_URL=mongodb://customer-db:27017/customerdb
    depends_on:
      - customer-db

  product-db:
    image: postgres:15
    environment:
      POSTGRES_DB: productdb
      POSTGRES_USER: user
      POSTGRES_PASSWORD: pass

  order-db:
    image: postgres:15
    environment:
      POSTGRES_DB: orderdb
      POSTGRES_USER: user
      POSTGRES_PASSWORD: pass

  customer-db:
    image: mongo:6

  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"

  message-broker:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"
```

---

## 📬 Message Broker

- **RabbitMQ** (hoặc Kafka) dùng để truyền sự kiện giữa các service.
- Khi đơn hàng được tạo, **Order Service** gửi thông điệp `OrderCreated` đến hàng đợi `order.created`.

---

## 📊 Các API tiêu chuẩn (CRUD)

### Product Service
- `POST /products`
- `GET /products/{id}`
- `PUT /products/{id}`
- `DELETE /products/{id}`

### Order Service
- `POST /orders`
- `GET /orders/{id}`
- `PUT /orders/{id}`
- `DELETE /orders/{id}`

### Customer Service
- `POST /customers`
- `GET /customers/{id}`
- `PUT /customers/{id}`
- `DELETE /customers/{id}`

---

## ✅ Công nghệ sử dụng

- ⚙️ **Spring Boot** / NodeJS / Go / .NET (tùy chọn tech stack mỗi service)
- 🧱 **PostgreSQL** cho Product & Order
- 🍃 **MongoDB** cho Customer
- 🌉 **API Gateway** (Spring Cloud Gateway hoặc NGINX)
- 📨 **RabbitMQ** (có thể thay bằng Kafka)
- 🐳 **Docker** & **Docker Compose**

---

## 🚀 Hướng phát triển tiếp theo

- Tích hợp **service discovery** (Eureka hoặc Consul)
- Thêm **Authentication & Authorization** qua API Gateway
- Tích hợp **OpenAPI / Swagger**
- Tích hợp **Logging, Monitoring (ELK, Prometheus + Grafana)**

---

## 📌 Ghi chú

- Đảm bảo mỗi service có database riêng và không chia sẻ schema.
- Giao tiếp giữa các service cần được kiểm soát kỹ để tránh tight coupling.
- Ưu tiên bất đồng bộ khi xử lý hậu cần hoặc các hành động không cần phản hồi ngay.

---

## 💬 Liên hệ phát triển

Nguyễn Nga - Backend Developer  
📧 Email: [nga@example.com]  
🌐 Github: [github.com/nguyennga](https://github.com/nguyennga)

