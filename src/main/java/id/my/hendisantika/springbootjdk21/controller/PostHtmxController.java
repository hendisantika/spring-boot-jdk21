package id.my.hendisantika.springbootjdk21.controller;

import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 25/08/25
 * Time: 04.37
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/htmx/posts")
public class PostHtmxController {

    private final PostService postService;

    @GetMapping
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();

        model.addAttribute("title", "Blog Page");
        model.addAttribute("content", "all-posts");
        model.addAttribute("posts", posts);
        return "index";
    }

    @PostMapping
    public String createPost(@RequestParam String text, Model model) {
        Post post = new Post();
        post.setText(text);
        postService.createPost(post);
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "blog/all-posts :: #posts-list";
    }

    @GetMapping("/{id}/edit")
    public String getPostForEdit(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "blog/edit-post-form :: #edit-form-container";
    }
}
