# Sử dụng image Node.js chính thức làm base image
FROM node:23 AS build

# Tạo thư mục làm việc trong container
WORKDIR /app

# Copy package.json và package-lock.json vào container
COPY package.json package-lock.json ./

# Cài đặt tất cả các dependency
RUN npm install

# Copy tất cả mã nguồn vào container
COPY . .

# Build ứng dụng React (tạo thư mục build)
RUN npm run build

# Sử dụng image Nginx để phục vụ ứng dụng đã build
FROM nginx:alpine

# Copy thư mục build từ bước trước vào thư mục public của Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# Mở port 80 để truy cập vào ứng dụng
EXPOSE 80

# Chạy Nginx để phục vụ ứng dụng
CMD ["nginx", "-g", "daemon off;"]
