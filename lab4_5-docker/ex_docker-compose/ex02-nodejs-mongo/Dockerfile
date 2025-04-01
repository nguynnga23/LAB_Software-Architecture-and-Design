# Sử dụng Node.js image
FROM node:14

# Thiết lập thư mục làm việc trong container
WORKDIR /usr/src/app

# Sao chép file package.json và package-lock.json
COPY package*.json ./

# Cài đặt các dependencies
RUN npm install

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Mở port cho ứng dụng
EXPOSE 3000

# Chạy ứng dụng khi container khởi động
CMD ["node", "app.js"]
