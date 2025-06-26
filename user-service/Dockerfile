FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY target/user-service-*.jar app.jar

# Cấu hình mặc định (sẽ bị ghi đè bởi docker-compose)
ENV SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/user_db"
ENV SPRING_DATASOURCE_USERNAME="root"
ENV SPRING_DATASOURCE_PASSWORD="root"

# Tuỳ chọn: Thêm health check
HEALTHCHECK --interval=30s CMD curl -f http://localhost:8081/actuator/health || exit 1

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]