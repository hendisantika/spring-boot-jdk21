package id.my.hendisantika.springbootjdk21.service;

import id.my.hendisantika.springbootjdk21.CiTestConfiguration;
import id.my.hendisantika.springbootjdk21.TestcontainersConfiguration;
import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.entity.User;
import id.my.hendisantika.springbootjdk21.repository.PostRepository;
import id.my.hendisantika.springbootjdk21.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TestcontainersConfiguration.class, CiTestConfiguration.class})
@ActiveProfiles({"test", "ci"})
@Transactional
class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .firstName("Test")
                .lastName("Author")
                .email("author@example.com")
                .encodedPassword("password")
                .roleId(1)
                .build();
        testUser = userRepository.save(testUser);
    }

    @Test
    void shouldCreatePost() {
        Post newPost = Post.builder()
                .text("New post content")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post savedPost = postService.createPost(newPost);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getText()).isEqualTo("New post content");
    }

    @Test
    void shouldFindAllPosts() {
        Post post1 = Post.builder()
                .text("Content 1")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post post2 = Post.builder()
                .text("Content 2")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> allPosts = postService.getAllPosts();

        assertThat(allPosts).hasSizeGreaterThanOrEqualTo(2);
        assertThat(allPosts).extracting(Post::getText)
                .contains("Content 1", "Content 2");
    }

    @Test
    void shouldFindPostById() {
        Post post = Post.builder()
                .text("Content for find by ID test")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        Post foundPost = postService.getPostById(savedPost.getId());

        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getText()).isEqualTo("Content for find by ID test");
    }

    @Test
    void shouldUpdatePost() {
        Post post = Post.builder()
                .text("Original content")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        Post updateData = Post.builder()
                .text("Updated content")
                .build();

        Post updatedPost = postService.updatePost(savedPost.getId(), updateData);

        assertThat(updatedPost.getText()).isEqualTo("Updated content");
        assertThat(updatedPost.getId()).isEqualTo(savedPost.getId());
    }

    @Test
    void shouldDeletePost() {
        Post post = Post.builder()
                .text("This post will be deleted")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        postService.deletePost(postId);

        Post deletedPost = postRepository.findById(postId).orElse(null);
        assertThat(deletedPost).isNull();
    }
}