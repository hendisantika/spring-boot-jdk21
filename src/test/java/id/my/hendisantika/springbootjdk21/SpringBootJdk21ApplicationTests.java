package id.my.hendisantika.springbootjdk21;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
class SpringBootJdk21ApplicationTests {

    @Test
    void contextLoads() {
    }

}
