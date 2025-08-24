package id.my.hendisantika.springbootjdk21.service;

import id.my.hendisantika.springbootjdk21.entity.Post;
import id.my.hendisantika.springbootjdk21.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 24/08/25
 * Time: 08.41
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
