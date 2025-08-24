package id.my.hendisantika.springbootjdk21.service;

import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceUnitTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post testPost;

    @BeforeEach
    void setUp() {
        testPost = Post.builder()
                .id(1L)
                .text("Test content")
                .dateAdded(LocalDateTime.now())
                .dateModified(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldGetAllPosts() {
        List<Post> posts = Collections.singletonList(testPost);
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getText()).isEqualTo("Test content");
        verify(postRepository).findAll();
    }

    @Test
    void shouldGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));

        Post result = postService.getPostById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo("Test content");
        verify(postRepository).findById(1L);
    }

    @Test
    void shouldCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        Post newPost = Post.builder().text("New content").build();
        Post result = postService.createPost(newPost);

        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo("Test content");
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void shouldUpdatePost() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        Post updateData = Post.builder().text("Updated content").build();
        Post result = postService.updatePost(1L, updateData);

        assertThat(result).isNotNull();
        verify(postRepository).findById(1L);
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void shouldDeletePost() {
        doNothing().when(postRepository).deleteById(anyLong());

        postService.deletePost(1L);

        verify(postRepository).deleteById(1L);
    }

    @Test
    void shouldReturnNullWhenPostNotFound() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        Post result = postService.getPostById(1L);

        assertThat(result).isNull();
        verify(postRepository).findById(1L);
    }
}