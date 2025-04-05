-- Tạo lại bảng AI_Matching
DROP TABLE IF EXISTS AI_Matching;

CREATE TABLE AI_Matching (
                             matchId BIGINT PRIMARY KEY AUTO_INCREMENT,
                             suggestedTeamId BIGINT NOT NULL,
                             matchScore FLOAT NOT NULL,
                             createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT FK_AI_Matching_suggestedTeam FOREIGN KEY (suggestedTeamId)
                                 REFERENCES teams (team_id) ON DELETE CASCADE
);

-- Chỉnh sửa cột student_id trong tasks để có thể NULL
ALTER TABLE tasks MODIFY COLUMN student_id BIGINT NULL;

-- Xóa khóa ngoại cũ nếu tồn tại
ALTER TABLE tasks DROP FOREIGN KEY IF EXISTS FKj2lmqre55iteg69kqwhxcyj7n;

-- Thêm lại khóa ngoại mới
ALTER TABLE tasks
    ADD CONSTRAINT FK_tasks_student
        FOREIGN KEY (student_id)
            REFERENCES student (user_id)
            ON DELETE SET NULL;
