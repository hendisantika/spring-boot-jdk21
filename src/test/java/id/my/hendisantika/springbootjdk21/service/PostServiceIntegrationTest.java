package id.my.hendisantika.springbootjdk21.service;

import id.my.hendisantika.springbootjdk21.TestcontainersConfiguration;
import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.entity.User;
import id.my.hendisantika.springbootjdk21.repository.PostRepository;
import id.my.hendisantika.springbootjdk21.repository.UserRepository;
import id.my.hendisantika.springbootjdk21.security.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
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
                .name("Test Author")
                .email("author@example.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();
        testUser = userRepository.save(testUser);
    }

    @Test
    void shouldCreatePost() {
        Post newPost = Post.builder()
                .title("New Post")
                .content("New post content")
                .author(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post savedPost = postService.createPost(newPost);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("New Post");
        assertThat(savedPost.getContent()).isEqualTo("New post content");
        assertThat(savedPost.getAuthor().getId()).isEqualTo(testUser.getId());
    }

    @Test
    void shouldFindAllPosts() {
        Post post1 = Post.builder()
                .title("Service Test Post 1")
                .content("Content 1")
                .author(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post post2 = Post.builder()
                .title("Service Test Post 2")
                .content("Content 2")
                .author(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> allPosts = postService.getAllPosts();

        assertThat(allPosts).hasSizeGreaterThanOrEqualTo(2);
        assertThat(allPosts).extracting(Post::getTitle)
                .contains("Service Test Post 1", "Service Test Post 2");
    }

    @Test
    void shouldFindPostById() {
        Post post = Post.builder()
                .title("Find By ID Test")
                .content("Content for find by ID test")
                .author(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        Optional<Post> foundPost = postService.findPostById(savedPost.getId());

        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("Find By ID Test");
        assertThat(foundPost.get().getAuthor().getId()).isEqualTo(testUser.getId());
    }

    @Test
    void shouldUpdatePost() {
        Post post = Post.builder()
                .title("Original Title")
                .content("Original content")
                .author(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        savedPost.setTitle("Updated Title");
        savedPost.setContent("Updated content");
        savedPost.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postService.updatePost(savedPost);

        assertThat(updatedPost.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedPost.getContent()).isEqualTo("Updated content");
        assertThat(updatedPost.getId()).isEqualTo(savedPost.getId());
    }

    @Test
    void shouldDeletePost() {
        Post post = Post.builder()
                .title("Post to Delete")
                .content("This post will be deleted")
                .author(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        postService.deletePost(postId);

        Optional<Post> deletedPost = postRepository.findById(postId);
        assertThat(deletedPost).isEmpty();
    }
}