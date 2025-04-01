# Bước 1: Xây dựng Image
``` bash
docker build -t my-react-app .
```
# Bước 2: Chạy Container
``` bash
docker run -d -p 3000:80 --name react-container my-react-app
```