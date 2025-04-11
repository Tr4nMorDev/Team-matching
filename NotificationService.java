package ut.edu.teammatching.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.exceptions.ResourceNotFoundException;
import ut.edu.teammatching.models.Notification;
import ut.edu.teammatching.repositories.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Tạo thông báo mới
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Lấy tất cả thông báo
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Lấy thông báo theo ID
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }

    // Lấy thông báo theo người nhận
    public List<Notification> getNotificationsByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    // Lấy thông báo chưa đọc theo người nhận
    public List<Notification> getUnreadNotificationsByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientIdAndIsReadFalse(recipientId);
    }

    // Đánh dấu thông báo là đã đọc
    public Notification markAsRead(Long id) {
        Notification notification = getNotificationById(id);
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    // Xóa thông báo
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
}