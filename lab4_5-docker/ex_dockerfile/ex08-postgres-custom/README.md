# Dockerized PostgreSQL with Custom Initialization

## Overview
This project provides a **custom PostgreSQL Docker image** based on `postgres:15`, with an automatic initialization script to create a database, user, and sample table when the container runs for the first time.

## Project Structure
```
postgres-custom/
│── Dockerfile        # Defines the PostgreSQL image and initialization
│── init.sql          # SQL script to create database, user, and table
│── README.md         # Documentation (this file)
```

## Requirements
- [Docker](https://www.docker.com/get-started) installed on your system

## Setup and Run
### 1️⃣ **Build the Docker Image**
Run the following command to build the custom PostgreSQL image:
```sh
docker build -t my-postgres .
```

### 2️⃣ **Run the Container**
```sh
docker run -d --name postgres-container -p 5432:5432 my-postgres
```
- `-d`: Runs the container in detached mode.
- `--name postgres-container`: Assigns a name to the container.
- `-p 5432:5432`: Maps the PostgreSQL port from the container to the host.

### 3️⃣ **Verify Database Initialization**
Access the PostgreSQL shell inside the container:
```sh
docker exec -it postgres-container psql -U admin -d mydatabase
```
Run the following SQL command to check if the `users` table was created:
```sql
\dt
```
Expected output:
```
           List of relations
 Schema | Name  | Type  | Owner
--------+-------+-------+-------
 public | users | table | admin
(1 row)
```

## Stop and Remove Container
To stop the running container:
```sh
docker stop postgres-container
```
To remove the container:
```sh
docker rm postgres-container
```

## Environment Variables
The following environment variables are set in the Dockerfile:
```dockerfile
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=defaultdb
```
You can override these when running the container:
```sh
docker run -d --name postgres-container -p 5432:5432 \
-e POSTGRES_USER=myuser -e POSTGRES_PASSWORD=mypassword -e POSTGRES_DB=mydb my-postgres
```

## Troubleshooting
### 🔹 Check container logs
```sh
docker logs postgres-container
```
### 🔹 Connect to the database manually
If PostgreSQL is running, you can connect from your local machine using:
```sh
psql -h localhost -p 5432 -U admin -d mydatabase
```

## Conclusion
This setup allows you to quickly deploy a PostgreSQL database with predefined schemas and users, making it ideal for development and testing environments.