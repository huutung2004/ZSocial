FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy file JAR (đổi tên thành app.jar để đơn giản)
COPY target/post-service-*.jar app.jar

# Cấu hình kết nối MySQL (có thể override bằng environment trong docker-compose)
ENV SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/post_db"
ENV SPRING_DATASOURCE_USERNAME="root"
ENV SPRING_DATASOURCE_PASSWORD="root"

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]