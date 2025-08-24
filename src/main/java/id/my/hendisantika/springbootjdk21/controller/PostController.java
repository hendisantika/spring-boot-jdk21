package id.my.hendisantika.springbootjdk21.controller;

import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.entity.User;
import id.my.hendisantika.springbootjdk21.security.RoleEnum;
import id.my.hendisantika.springbootjdk21.service.AuthService;
import id.my.hendisantika.springbootjdk21.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 25/08/25
 * Time: 04.35
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final AuthService authService;
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getAllPosts(HttpServletRequest request) {
        Optional<User> userOptional = authService.getUserFromSession(request);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Post> posts = postService.getAllPosts();

            if (user.getRoleId() == RoleEnum.ADMIN.getRoleId()) {
                posts.forEach(post -> post.setText("ADMIN - " + post.getText()));
            }

            return new ResponseEntity<>(posts, HttpStatus.OK);
        }

        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        return postService.updatePost(id, updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
