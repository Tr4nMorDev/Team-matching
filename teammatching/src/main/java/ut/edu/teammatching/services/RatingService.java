package ut.edu.teammatching.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.exceptions.ResourceNotFoundException;
import ut.edu.teammatching.models.Rating;
import ut.edu.teammatching.repositories.RatingRepository;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // Lưu một đánh giá mới
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Lấy tất cả đánh giá
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    // Lấy đánh giá theo ID
    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found with id: " + id));
    }

    // Lấy đánh giá theo sinh viên được đánh giá
    public List<Rating> getRatingsByStudentId(Long studentId) {
        return ratingRepository.findByRatedStudentId(studentId);
    }

    // Lấy đánh giá theo giảng viên được đánh giá
    public List<Rating> getRatingsByLecturerId(Long lecturerId) {
        return ratingRepository.findByRatedLecturerId(lecturerId);
    }

    // Cập nhật đánh giá
    public Rating updateRating(Long id, Rating ratingDetails) {
        Rating rating = getRatingById(id);
        rating.setRating(ratingDetails.getRating());
        rating.setFeedback(ratingDetails.getFeedback());
        return ratingRepository.save(rating);
    }

    // Xóa đánh giá
    public void deleteRating(Long id) {
        Rating rating = getRatingById(id);
        ratingRepository.delete(rating);
    }
}