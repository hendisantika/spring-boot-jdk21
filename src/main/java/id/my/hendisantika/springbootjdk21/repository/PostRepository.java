package id.my.hendisantika.springbootjdk21.repository;

import id.my.hendisantika.springbootjdk21.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 24/08/25
 * Time: 08.31
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
