package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.teammatching.models.Notification;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Tìm tất cả thông báo theo người nhận
    List<Notification> findByRecipientId(Long recipientId);

    // Tìm thông báo chưa đọc theo người nhận
    List<Notification> findByRecipientIdAndIsReadFalse(Long recipientId);
}