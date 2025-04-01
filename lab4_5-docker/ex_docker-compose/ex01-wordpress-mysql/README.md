# 🚀 Deploy WordPress with MySQL using Docker Compose

## 📌 Overview
This project sets up **WordPress with MySQL** using **Docker Compose**, ensuring persistent storage through volumes.

## 📂 Project Structure
```
wordpress-mysql/
│── docker-compose.yml  # Defines WordPress and MySQL services
│── README.md           # Documentation (this file)
```

## 🛠️ Requirements
- [Docker](https://www.docker.com/get-started) installed on your system
- [Docker Compose](https://docs.docker.com/compose/install/) installed

## 🚀 Setup and Run
### 1️⃣ **Start the Containers**
Run the following command to build and start WordPress & MySQL:
```sh
docker-compose up -d
```
- `-d`: Runs the services in detached mode (background).

### 2️⃣ **Access WordPress**
Open a browser and go to:
```
http://localhost:8080
```
Follow the installation steps and use the following database credentials:
- **Database Name:** `wordpress`
- **Username:** `wpuser`
- **Password:** `wppassword`
- **Database Host:** `mysql`

### 3️⃣ **Stop and Remove Containers**
To stop the services:
```sh
docker-compose down
```
This will remove the containers but keep the volumes (data will persist).

## 📋 Configuration Details
### 🔹 **Docker Compose Services**
1. **MySQL Service (`mysql-container`)**
   - Uses `mysql:5.7`
   - Stores data in a volume `/var/lib/mysql`
   - Environment variables:
     ```yaml
     MYSQL_ROOT_PASSWORD: rootpassword
     MYSQL_DATABASE: wordpress
     MYSQL_USER: wpuser
     MYSQL_PASSWORD: wppassword
     ```

2. **WordPress Service (`wordpress-container`)**
   - Uses `wordpress:latest`
   - Exposes port **8080 → 80**
   - Depends on MySQL service
   - Stores data in a volume `/var/www/html`
   - Connects to MySQL via network `wp_network`

### 🔹 **Persistent Data Volumes**
- `mysql_data`: Stores MySQL database files
- `wp_data`: Stores WordPress files

## 🛑 Troubleshooting
### 🔹 Check Running Containers
```sh
docker ps
```

### 🔹 View Container Logs
```sh
docker logs wordpress-container
```
```sh
docker logs mysql-container
```

### 🔹 Enter MySQL Container
```sh
docker exec -it mysql-container mysql -u wpuser -p
```

### 🔹 Enter WordPress Container
```sh
docker exec -it wordpress-container bash
```

## 🎯 Conclusion
This setup allows you to deploy **WordPress with MySQL** in a **containerized environment**, ensuring easy setup, scalability, and data persistence. 🚀

