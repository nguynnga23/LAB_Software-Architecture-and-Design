# Tổng quan về Workflow

Hệ thống bao gồm ba dịch vụ chính:

- **Order Service**: Xử lý việc đặt hàng.
- **Inventory Service**: Quản lý và cập nhật tồn kho.
- **Notification Service**: Gửi thông báo qua email khi đơn hàng được đặt.

Các dịch vụ này giao tiếp với nhau thông qua **Kafka** bằng cách phát và tiêu thụ các sự kiện.

---

## Chi tiết Workflow
![System Architecture Diagram](./src/main/resources/images/work-flow.png)
### Bước 1: Đặt hàng

- Người dùng gửi yêu cầu đặt hàng qua API `/api/orders` của `OrderController`.
- `OrderController` gọi `OrderService` để xử lý logic đặt hàng:
  - Tạo một đối tượng `Order` với trạng thái ban đầu là `PENDING`.
  - Lưu đơn hàng vào cơ sở dữ liệu thông qua `OrderJpaAdapter`.
  - Phát sự kiện `order-placed` lên Kafka với thông tin đơn hàng.

### Bước 2: Cập nhật tồn kho

- `InventoryService` lắng nghe sự kiện `order-placed` từ Kafka.
- Khi nhận được sự kiện:
  - Trích xuất thông tin sản phẩm và số lượng từ thông điệp Kafka.
  - Kiểm tra tồn kho trong cơ sở dữ liệu thông qua `StockRepository`.
  - Cập nhật số lượng tồn kho và lưu lại thay đổi.

### Bước 3: Gửi thông báo

- `NotificationService` lắng nghe sự kiện `order-placed` từ Kafka.
- Khi nhận được sự kiện:
  - Trích xuất email khách hàng và thông tin đơn hàng từ thông điệp Kafka.
  - Gửi email xác nhận đơn hàng qua SMTP bằng `JavaMailSender`.

---

## Các thành phần chính

### Kafka

- **Producer**: `OrderService` sử dụng `KafkaTemplate` để phát sự kiện `order-placed`.
- **Consumer**:
  - `InventoryService` và `NotificationService` sử dụng `@KafkaListener` để tiêu thụ sự kiện `order-placed`.

### Cơ sở dữ liệu (PostgreSQL)

- `OrderRepository`: Lưu và truy xuất thông tin đơn hàng.
- `StockRepository`: Lưu và truy xuất thông tin tồn kho.

### Email

- `NotificationService` sử dụng cấu hình SMTP trong `application.properties` để gửi email xác nhận đơn hàng.

---

## Luồng dữ liệu

1. **API Request**: Người dùng gửi yêu cầu đặt hàng qua REST API.
2. **Kafka Event**: Sự kiện `order-placed` được phát lên Kafka.
3. **Inventory Update**: `InventoryService` tiêu thụ sự kiện và cập nhật tồn kho.
4. **Email Notification**: `NotificationService` tiêu thụ sự kiện và gửi email xác nhận.

---

## Công nghệ sử dụng

- **Spring Boot**: Framework chính để xây dựng các dịch vụ.
- **Apache Kafka**: Nền tảng truyền thông sự kiện.
- **PostgreSQL**: Cơ sở dữ liệu quan hệ để lưu trữ đơn hàng và tồn kho.
- **JavaMailSender**: Gửi email thông báo.
- **Docker**: Container hóa Kafka và PostgreSQL.

---

## Điểm mạnh

- **Tách biệt dịch vụ**: Các dịch vụ độc lập, giao tiếp qua Kafka.
- **Khả năng mở rộng**: Dễ dàng thêm dịch vụ mới bằng cách lắng nghe các sự kiện Kafka.
- **Độ tin cậy**: Kafka đảm bảo tính bền vững của sự kiện.

---

## Hạn chế

- **Xử lý lỗi**: Chưa có cơ chế xử lý lỗi khi Kafka hoặc email gặp sự cố.
- **Bảo mật**: Thông tin email và mật khẩu trong `application.properties` cần được bảo vệ tốt hơn (ví dụ: sử dụng Vault hoặc biến môi trường).

---

## Đề xuất cải tiến

- **Thêm cơ chế retry**: Xử lý lỗi khi gửi email hoặc cập nhật tồn kho thất bại.
- **Bảo mật thông tin nhạy cảm**: Sử dụng biến môi trường hoặc công cụ quản lý bí mật.
- **Giám sát**: Tích hợp Prometheus và Grafana để theo dõi hệ thống.

---