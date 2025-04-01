# Sử dụng PostgreSQL 15
FROM postgres:15

# Thiết lập biến môi trường mặc định
ENV POSTGRES_USER=root
ENV POSTGRES_PASSWORD=root
ENV POSTGRES_DB=defaultdb

# Sao chép file init.sql vào thư mục initdb
COPY init.sql /docker-entrypoint-initdb.d/

# Expose cổng PostgreSQL
EXPOSE 5432
