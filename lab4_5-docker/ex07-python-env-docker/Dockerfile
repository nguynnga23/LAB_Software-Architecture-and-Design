# Sử dụng Python image mới nhất
FROM python:3.9

# Đặt thư mục làm việc
WORKDIR /app

# Sao chép file app.py vào container
COPY app.py .

# Thiết lập biến môi trường mặc định
ENV APP_ENV=development

# Chạy ứng dụng
CMD ["python", "app.py"]
