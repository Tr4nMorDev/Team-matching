-- Bảng User
CREATE TABLE User (
                      userId BIGINT PRIMARY KEY AUTO_INCREMENT,
                      userName VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      role ENUM('STUDENT', 'LECTURER') NOT NULL,
                      fullName VARCHAR(255),
                      gender ENUM('MALE', 'FEMALE', 'OTHER'),
                      profilePictureUrl VARCHAR(255),
                      email VARCHAR(255) UNIQUE,
                      skills TEXT,
                      hobby TEXT,
                      projects TEXT,
                      phoneNumber VARCHAR(20),
);

-- Bảng Student (Kế thừa từ User)
CREATE TABLE Student (
                         userId BIGINT PRIMARY KEY,
                         major VARCHAR(255) NOT NULL,
                         term INT NOT NULL,
                         FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Bảng Lecturer (Kế thừa từ User)
CREATE TABLE Lecturer (
                          userId BIGINT PRIMARY KEY,
                          department VARCHAR(255) NOT NULL,
                          ResearchAreas TEXT,
                          FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Bảng Team
CREATE TABLE Team (
                      teamId BIGINT PRIMARY KEY AUTO_INCREMENT,
                      teamName VARCHAR(255) NOT NULL,
                      teamType ENUM('ACADEMIC', 'PERSIONAL', 'LECTURER_GUIDED') NOT NULL,
                      leaderId BIGINT NOT NULL,
                      FOREIGN KEY (leaderId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Bảng Role (Leader có thể tạo mới)
CREATE TABLE Role (
                      roleId BIGINT PRIMARY KEY AUTO_INCREMENT,
                      roleName VARCHAR(255) NOT NULL,
                      teamId BIGINT NOT NULL,
                      FOREIGN KEY (teamId) REFERENCES Team(teamId) ON DELETE CASCADE
);

-- Bảng Task
CREATE TABLE Task (
                      taskId BIGINT PRIMARY KEY AUTO_INCREMENT,
                      taskName VARCHAR(255) NOT NULL,
                      description TEXT,
                      status ENUM('IN_PROGRESS', 'COMPLETED', 'NOT_COMPLETED') DEFAULT 'IN_PROGRESS',
                      deadline DATE,
                      assigneeId BIGINT,
                      teamId BIGINT NOT NULL,
                      FOREIGN KEY (assigneeId) REFERENCES User(userId) ON DELETE SET NULL,
                      FOREIGN KEY (teamId) REFERENCES Team(teamId) ON DELETE CASCADE
);

-- Bảng Post
CREATE TABLE Post (
                      postId BIGINT PRIMARY KEY AUTO_INCREMENT,
                      content TEXT NOT NULL,
                      images TEXT,
                      videos TEXT,
                      authorId BIGINT NOT NULL,
                      FOREIGN KEY (authorId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Bảng Comment
CREATE TABLE Comment (
                         commentId BIGINT PRIMARY KEY AUTO_INCREMENT,
                         content TEXT NOT NULL,
                         postId BIGINT NOT NULL,
                         authorId BIGINT NOT NULL,
                         FOREIGN KEY (postId) REFERENCES Post(postId) ON DELETE CASCADE,
                         FOREIGN KEY (authorId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Bảng Message
CREATE TABLE Message (
                         messageId BIGINT PRIMARY KEY AUTO_INCREMENT,
                         senderId BIGINT NOT NULL,
                         receiverId BIGINT,
                         teamId BIGINT,
                         content TEXT NOT NULL,
                         FOREIGN KEY (senderId) REFERENCES User(userId) ON DELETE CASCADE,
                         FOREIGN KEY (receiverId) REFERENCES User(userId) ON DELETE CASCADE,
                         FOREIGN KEY (teamId) REFERENCES Team(teamId) ON DELETE CASCADE
);

-- Bảng Notification
CREATE TABLE Notification (
                              notificationId BIGINT PRIMARY KEY AUTO_INCREMENT,
                              content TEXT NOT NULL,
                              type ENUM('DEADLINE', 'MESSAGE', 'INVITE') NOT NULL,
                              recipientId BIGINT NOT NULL,
                              createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              isRead BOOLEAN DEFAULT FALSE,
                              FOREIGN KEY (recipientId) REFERENCES User(userId) ON DELETE CASCADE
);

-- Bảng AI_Matching (Lưu lịch sử gợi ý nhóm)
CREATE TABLE AI_Matching (
                             matchId BIGINT PRIMARY KEY AUTO_INCREMENT,
                             userId BIGINT NOT NULL,
                             suggestedTeamId BIGINT NOT NULL,
                             matchScore FLOAT NOT NULL,
                             createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE,
                             FOREIGN KEY (suggestedTeamId) REFERENCES Team(teamId) ON DELETE CASCADE
);

-- Bảng Rating (Đánh giá thành viên)
CREATE TABLE Rating (
                        ratingId BIGINT PRIMARY KEY AUTO_INCREMENT,
                        reviewerId BIGINT NOT NULL,
                        ratedUserId BIGINT NOT NULL,
                        teamId BIGINT NOT NULL,
                        rating FLOAT NOT NULL CHECK (rating BETWEEN 0 AND 5),
                        feedback TEXT,
                        FOREIGN KEY (reviewerId) REFERENCES User(userId) ON DELETE CASCADE,
                        FOREIGN KEY (ratedUserId) REFERENCES User(userId) ON DELETE CASCADE,
                        FOREIGN KEY (teamId) REFERENCES Team(teamId) ON DELETE CASCADE
);
