package ut.edu.teammatching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.BlogCreateRequest;
import ut.edu.teammatching.models.Blog;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.BlogRepository;
import ut.edu.teammatching.repositories.UserRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blogs")
@CrossOrigin(origins = "*")
public class BlogController {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir:uploads/imagespost}")
    private String uploadDir;

    @Autowired
    public BlogController(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        try {
            List<Blog> blogs = blogRepository.findAll();
            return ResponseEntity.ok(blogs);
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
            return ResponseEntity.internalServerError().body("Error fetching blog: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody BlogCreateRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
//         Check if user is authenticated
        System.out.println("UserDetails: " + userDetails);
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        // Find user by email/username from token
        User author = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Validate request
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content cannot be empty");
        }

        // Create new Blog entity
        Blog blog = new Blog();
        blog.setContent(request.getContent());
        blog.setAuthor(author);

        // Handle image if provided
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            try {
                String base64 = String.valueOf(request.getImages());
                // Remove data URI prefix if present (e.g., "data:image/png;base64,")
                if (base64.contains(",")) {
                    base64 = base64.split(",")[1];
                }

                // Decode base64 to bytes
                byte[] imageBytes = Base64.getDecoder().decode(base64);

                // Validate image format
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                if (image == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid image format");
                }

                // Generate unique filename
                String fileName = UUID.randomUUID() + ".png";
                Path uploadPath = Paths.get(uploadDir);
                Path filePath = uploadPath.resolve(fileName);

                // Create upload directory if it doesn't exist
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Save image to file system
                Files.write(filePath, imageBytes);

                // Set image path in blog (relative path for frontend access)
                blog.setImages("/imagespost/" + fileName);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid base64 string");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save image");
            }
        }

        // Save blog to database
        Blog savedBlog = blogRepository.save(blog);
        return ResponseEntity.ok(savedBlog);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Blog updatedBlog) {
        try {
            return blogRepository.findById(id)
                    .map(blog -> {
                        blog.setContent(updatedBlog.getContent());
                        blog.setImages(updatedBlog.getImages());
                        if (updatedBlog.getLikeCount() != null) {
                            blog.setLikeCount(updatedBlog.getLikeCount());
                        }
                        Blog savedBlog = blogRepository.save(blog);
                        return ResponseEntity.ok(savedBlog);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating blog: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            return blogRepository.findById(id)
                    .map(blog -> {
                        blogRepository.delete(blog);
                        return ResponseEntity.ok().build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting blog: " + e.getMessage());
        }
    }
}
