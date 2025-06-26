# Sử dụng image Java phù hợp
FROM eclipse-temurin:21-jre-jammy

# Tạo thư mục app
WORKDIR /app

# Copy file JAR vào image
COPY target/*.jar app.jar

# Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]