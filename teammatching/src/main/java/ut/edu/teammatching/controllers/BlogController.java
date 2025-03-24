package ut.edu.teammatching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.repositories.BlogRepository;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.models.Blog;
import ut.edu.teammatching.models.User;

import java.util.List;

@RestController
@RequestMapping("/blog")
@CrossOrigin(origins = "*")
public class BlogController {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Autowired
    public BlogController(BlogRepository postRepository, UserRepository userRepository) {
        this.blogRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        try {
            List<Blog> posts = blogRepository.findAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching posts: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            return blogRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching post: " + e.getMessage());
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createPost(@RequestBody Blog post, @RequestParam Long userId) {
        try {
            return userRepository.findById(userId)
                    .map(user -> {
                        post.setAuthor(user);
                        post.setLikeCount(0); // Ensure likeCount starts at 0
                        Blog savedPost = blogRepository.save(post);
                        return ResponseEntity.ok(savedPost);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating post: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Blog updatedPost) {
        try {
            return blogRepository.findById(id)
                    .map(post -> {
                        post.setContent(updatedPost.getContent());
                        post.setImages(updatedPost.getImages());
                        if (updatedPost.getLikeCount() != null) {
                            post.setLikeCount(updatedPost.getLikeCount());
                        }
                        Blog savedPost = blogRepository.save(post);
                        return ResponseEntity.ok(savedPost);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating post: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            return blogRepository.findById(id)
                    .map(post -> {
                        blogRepository.delete(post);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting post: " + e.getMessage());
        }
    }
}
