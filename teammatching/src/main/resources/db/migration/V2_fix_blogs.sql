ALTER TABLE blogs
MODIFY like_count INT DEFAULT 0;

INSERT INTO blogs (content, images, user_id) VALUES
('Check ảnh hoàng hôn chill nè 🌇', 'http://localhost:8080/imagespost/image1.jpg', 10),
('Cà phê cuối tuần với bạn 💬', 'http://localhost:8080/imagespost/image2.jpg', 11),
('Ảnh buổi thuyết trình hôm qua 😎', 'http://localhost:8080/imagespost/image3.jpg', 10),
('View học nhóm ở thư viện nè 📚', 'http://localhost:8080/imagespost/image4.jpg', 11);