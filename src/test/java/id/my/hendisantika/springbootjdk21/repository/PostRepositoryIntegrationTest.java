package id.my.hendisantika.springbootjdk21.repository;

import id.my.hendisantika.springbootjdk21.TestcontainersConfiguration;
import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.entity.User;
import id.my.hendisantika.springbootjdk21.security.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PostRepositoryIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindPost() {
        User author = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post = Post.builder()
                .title("Test Post")
                .content("This is a test post content")
                .author(savedAuthor)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("Test Post");
        assertThat(savedPost.getContent()).isEqualTo("This is a test post content");
        assertThat(savedPost.getAuthor().getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldFindPostById() {
        User author = User.builder()
                .name("Jane Doe")
                .email("jane@example.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post = Post.builder()
                .title("Another Test Post")
                .content("Another test post content")
                .author(savedAuthor)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        Optional<Post> foundPost = postRepository.findById(savedPost.getId());

        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("Another Test Post");
        assertThat(foundPost.get().getAuthor().getEmail()).isEqualTo("jane@example.com");
    }

    @Test
    void shouldFindAllPosts() {
        User author = User.builder()
                .name("Test User")
                .email("test@example.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post1 = Post.builder()
                .title("First Post")
                .content("First post content")
                .author(savedAuthor)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post post2 = Post.builder()
                .title("Second Post")
                .content("Second post content")
                .author(savedAuthor)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> allPosts = postRepository.findAll();

        assertThat(allPosts).hasSize(2);
        assertThat(allPosts).extracting(Post::getTitle)
                .containsExactlyInAnyOrder("First Post", "Second Post");
    }

    @Test
    void shouldDeletePost() {
        User author = User.builder()
                .name("Delete Test User")
                .email("delete@example.com")
                .password("password")
                .role(RoleEnum.USER)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post = Post.builder()
                .title("Post to Delete")
                .content("This post will be deleted")
                .author(savedAuthor)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        postRepository.deleteById(postId);

        Optional<Post> deletedPost = postRepository.findById(postId);
        assertThat(deletedPost).isEmpty();
    }
}