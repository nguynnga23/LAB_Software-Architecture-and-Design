# Stage 1: Build stage
FROM node:18 AS builder

# Đặt thư mục làm việc trong container
WORKDIR /app

# Sao chép package.json và cài đặt dependencies
COPY package.json .
RUN npm install --production

# Sao chép toàn bộ code vào container
COPY . .

# Stage 2: Run stage (Sử dụng Alpine để tối ưu kích thước image)
FROM node:18-alpine AS runner

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép code từ stage 1
COPY --from=builder /app .

# Expose cổng 3000
EXPOSE 3000

# Chạy ứng dụng
CMD ["node", "server.js"]
