import os

# Đọc biến môi trường APP_ENV
app_env = os.getenv("APP_ENV", "production")  # Giá trị mặc định là 'production'

print(f"Application is running in {app_env} mode!")
