# Bước 1: Xây dựng ứng dụng với Maven và Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép toàn bộ mã nguồn vào container
COPY . .

# Chạy Maven để build ứng dụng (bỏ qua test để giảm thời gian xây dựng)
RUN mvn clean package -DskipTests

# Bước 2: Tạo image chạy ứng dụng với Java 21
FROM eclipse-temurin:21

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR đã được build từ bước 1 vào image cuối
COPY --from=builder /app/target/*.jar app.jar

# Mở cổng 8080 để ứng dụng có thể truy cập
EXPOSE 8080

# Khởi động ứng dụng khi container bắt đầu chạy
ENTRYPOINT ["java", "-jar", "app.jar"]
