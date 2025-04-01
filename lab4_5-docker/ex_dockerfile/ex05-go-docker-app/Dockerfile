# Sử dụng base image của Golang
FROM golang:latest

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn vào container
COPY . .

# Biên dịch ứng dụng Go thành binary
RUN go mod init myapp && go mod tidy
RUN go build -o myapp

# Expose cổng 8080
EXPOSE 8080

# Chạy ứng dụng
CMD ["./myapp"]
