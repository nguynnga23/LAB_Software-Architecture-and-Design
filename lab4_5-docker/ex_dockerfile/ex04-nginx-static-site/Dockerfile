# Sử dụng image Nginx mới nhất
FROM nginx:latest

# Sao chép file index.html vào thư mục gốc của Nginx
COPY index.html /usr/share/nginx/html/index.html

# Expose port 80 để truy cập web
EXPOSE 80

# Khởi chạy Nginx
CMD ["nginx", "-g", "daemon off;"]
