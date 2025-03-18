# Xóa container cũ nếu có
docker rm -f flask-container

# Xóa image cũ nếu có
docker rmi my-flask-app

# Xây dựng lại image
docker build -t my-flask-app .

# Chạy container mới
docker run -d -p 5000:5000 --name flask-container my-flask-app
