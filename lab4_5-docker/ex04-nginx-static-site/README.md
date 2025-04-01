```bash
docker build -t my-nginx-site .
```
```bash
docker run -d -p 8080:80 --name nginx-container my-nginx-site
```
```bash
http://localhost:8080
```
