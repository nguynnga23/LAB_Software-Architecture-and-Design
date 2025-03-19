# Tổng quan về Công nghệ và Design Pattern

## Message Queue + JWT
- **Message Queue**: Hệ thống hàng đợi tin nhắn được sử dụng để truyền thông tin giữa các dịch vụ hoặc ứng dụng một cách bất đồng bộ. Giúp giảm tải và tăng tính linh hoạt trong hệ thống phân tán.
- **JWT (JSON Web Token)**: Một chuẩn mở để xác thực và trao đổi thông tin giữa các bên. JWT bao gồm 3 phần: Header, Payload, và Signature, thường được dùng trong các hệ thống phân tán để xác minh danh tính người dùng.

## Design Pattern
### Singleton, Factory, State, Decorator, Strategy
- **Singleton**: Đảm bảo một lớp chỉ có một instance duy nhất và cung cấp một điểm truy cập toàn cục tới instance đó.
- **Factory**: Tạo ra các đối tượng mà không cần chỉ định rõ ràng lớp cụ thể của chúng, giúp tăng tính linh hoạt và giảm sự phụ thuộc.
- **State**: Cho phép một đối tượng thay đổi hành vi của nó khi trạng thái bên trong thay đổi, giống như nó thay đổi lớp của chính mình.
- **Decorator**: Thêm tính năng mới cho đối tượng một cách động mà không cần thay đổi mã nguồn của lớp gốc.
- **Strategy**: Định nghĩa một tập hợp các thuật toán, đóng gói từng thuật toán và cho phép hoán đổi chúng dễ dàng trong quá trình chạy.

### Observer, Adapter, Composite
- **Observer**: Thiết lập quan hệ một-nhiều giữa các đối tượng, khi trạng thái của một đối tượng thay đổi, tất cả các đối tượng phụ thuộc sẽ được thông báo và cập nhật tự động.
- **Adapter**: Chuyển đổi giao diện của một lớp thành giao diện khác mà client mong đợi, giúp các lớp không tương thích có thể làm việc cùng nhau.
- **Composite**: Tổ chức các đối tượng thành cấu trúc cây để biểu diễn các phân cấp phần-toàn bộ, cho phép xử lý đồng nhất giữa đối tượng đơn lẻ và tập hợp.

## Docker
- **Docker**: Công nghệ container hóa cho phép đóng gói ứng dụng và các phụ thuộc của nó vào một đơn vị độc lập (container), đảm bảo tính nhất quán giữa các môi trường phát triển, kiểm thử và sản xuất.

## Docker Compose
- **Docker Compose**: Công cụ để định nghĩa và chạy nhiều container Docker cùng lúc thông qua một file YAML. Giúp đơn giản hóa việc quản lý các ứng dụng đa container.

## Microservice
- **Microservice**: Một kiến trúc phát triển phần mềm chia ứng dụng thành các dịch vụ nhỏ, độc lập, giao tiếp qua API. Mỗi dịch vụ tập trung vào một chức năng cụ thể và có thể được triển khai riêng lẻ.
- **Ưu điểm**: Tăng tính mở rộng, dễ bảo trì, và cho phép sử dụng công nghệ đa dạng cho từng dịch vụ.

### Resilent4J
- **Resilent4J**: Thư viện Java hỗ trợ xây dựng các microservice bền bỉ bằng cách cung cấp các cơ chế như Circuit Breaker, Retry, Rate Limiter, và Bulkhead. Giúp hệ thống xử lý lỗi hiệu quả và tránh hiệu ứng domino khi một dịch vụ gặp sự cố.

## Redis (Online Tools)
- **Redis**: Cơ sở dữ liệu NoSQL lưu trữ dữ liệu dạng key-value trong bộ nhớ, được sử dụng để tăng tốc độ truy cập dữ liệu (cache), quản lý session, hoặc xử lý hàng đợi.
- **Online Tools**: Các công cụ trực tuyến như Redis Commander hoặc RedisInsight có thể được dùng để quản lý và theo dõi dữ liệu Redis một cách trực quan.

## Gitlab CI/CD (Github Action)
- **Gitlab CI/CD**: Hệ thống tích hợp liên tục (CI) và triển khai liên tục (CD) được tích hợp trong Gitlab. Sử dụng file `.gitlab-ci.yml` để tự động hóa quy trình build, test và deploy.
- **Github Actions**: Tương tự Gitlab CI/CD, Github Actions là một công cụ CI/CD của Github, cho phép tự động hóa quy trình làm việc thông qua các workflow được định nghĩa trong file YAML.

---

*Ghi chú: Tài liệu này có thể được mở rộng thêm chi tiết tùy theo yêu cầu cụ thể của dự án hoặc ứng dụng thực tế.*