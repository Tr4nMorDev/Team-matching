package ut.edu.teammatching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.repositories.PostRepository;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.models.Post;
import ut.edu.teammatching.models.User;

import java.util.List;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        try {
            List<Post> posts = postRepository.findAll();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching posts: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            return postRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching post: " + e.getMessage());
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createPost(@RequestBody Post post, @RequestParam Long userId) {
        try {
            return userRepository.findById(userId)
                    .map(user -> {
                        post.setAuthor(user);
                        post.setLikeCount(0); // Ensure likeCount starts at 0
                        Post savedPost = postRepository.save(post);
                        return ResponseEntity.ok(savedPost);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating post: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        try {
            return postRepository.findById(id)
                    .map(post -> {
                        post.setContent(updatedPost.getContent());
                        post.setImages(updatedPost.getImages());
                        if (updatedPost.getLikeCount() != null) {
                            post.setLikeCount(updatedPost.getLikeCount());
                        }
                        Post savedPost = postRepository.save(post);
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
            return postRepository.findById(id)
                    .map(post -> {
                        postRepository.delete(post);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting post: " + e.getMessage());
        }
    }
}
