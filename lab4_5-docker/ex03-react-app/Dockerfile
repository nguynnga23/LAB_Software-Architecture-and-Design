FROM node:18-alpine AS build

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm install

COPY . .
RUN npm run build

# -------------------------------------
# Tạo stage mới để chạy ứng dụng (sử dụng Nginx)
FROM nginx:alpine

# Sao chép file build vào thư mục phục vụ của Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Expose cổng 80
EXPOSE 80

# Chạy Nginx
CMD ["nginx", "-g", "daemon off;"]