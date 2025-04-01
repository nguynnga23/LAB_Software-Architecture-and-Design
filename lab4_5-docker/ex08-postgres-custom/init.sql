-- Tạo database tùy chỉnh
CREATE DATABASE mydatabase;

-- Kết nối vào database
\c mydatabase;

-- Tạo bảng ví dụ
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Tạo user và cấp quyền
CREATE USER myuser WITH ENCRYPTED PASSWORD 'mypassword';
GRANT ALL PRIVILEGES ON DATABASE mydatabase TO myuser;
