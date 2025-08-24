package id.my.hendisantika.springbootjdk21.repository;

import id.my.hendisantika.springbootjdk21.CiTestConfiguration;
import id.my.hendisantika.springbootjdk21.TestcontainersConfiguration;
import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.entity.User;
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
@Import({TestcontainersConfiguration.class, CiTestConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test", "ci"})
class PostRepositoryIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindPost() {
        User author = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .encodedPassword("password")
                .roleId(1)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post = Post.builder()
                .text("This is a test post content")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getText()).isEqualTo("This is a test post content");
    }

    @Test
    void shouldFindPostById() {
        User author = User.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .encodedPassword("password")
                .roleId(1)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post = Post.builder()
                .text("Another test post content")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);

        Optional<Post> foundPost = postRepository.findById(savedPost.getId());

        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getText()).isEqualTo("Another test post content");
    }

    @Test
    void shouldFindAllPosts() {
        User author = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .encodedPassword("password")
                .roleId(1)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post1 = Post.builder()
                .text("First post content")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post post2 = Post.builder()
                .text("Second post content")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> allPosts = postRepository.findAll();

        assertThat(allPosts).hasSize(2);
        assertThat(allPosts).extracting(Post::getText)
                .containsExactlyInAnyOrder("First post content", "Second post content");
    }

    @Test
    void shouldDeletePost() {
        User author = User.builder()
                .firstName("Delete")
                .lastName("User")
                .email("delete@example.com")
                .encodedPassword("password")
                .roleId(1)
                .build();

        User savedAuthor = userRepository.save(author);

        Post post = Post.builder()
                .text("This post will be deleted")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();

        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        postRepository.deleteById(postId);

        Optional<Post> deletedPost = postRepository.findById(postId);
        assertThat(deletedPost).isEmpty();
    }
}