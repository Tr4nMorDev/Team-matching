package ut.edu.teammatching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.models.Rating;
import ut.edu.teammatching.services.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Tạo đánh giá mới
    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating createdRating = ratingService.createRating(rating);
        return ResponseEntity.ok(createdRating);
    }

    // Lấy tất cả đánh giá
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    // Lấy đánh giá theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
        Rating rating = ratingService.getRatingById(id);
        return ResponseEntity.ok(rating);
    }

    // Lấy đánh giá theo sinh viên
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Rating>> getRatingsByStudentId(@PathVariable Long studentId) {
        List<Rating> ratings = ratingService.getRatingsByStudentId(studentId);
        return ResponseEntity.ok(ratings);
    }

    // Lấy đánh giá theo giảng viên
    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<List<Rating>> getRatingsByLecturerId(@PathVariable Long lecturerId) {
        List<Rating> ratings = ratingService.getRatingsByLecturerId(lecturerId);
        return ResponseEntity.ok(ratings);
    }

    // Cập nhật đánh giá
    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @RequestBody Rating ratingDetails) {
        Rating updatedRating = ratingService.updateRating(id, ratingDetails);
        return ResponseEntity.ok(updatedRating);
    }

    // Xóa đánh giá
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}