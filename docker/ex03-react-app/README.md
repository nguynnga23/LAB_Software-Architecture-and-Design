# Bước 1: Xây dựng image
docker build -t my-react-app .
# Bước 2: Chạy container
docker run -d -p 3000:80 --name react-container my-react-app