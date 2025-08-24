package id.my.hendisantika.springbootjdk21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpringBootJdk21ApplicationTests {

    @Test
    void applicationClassExists() {
        // Fast unit test - just verify the class exists
        assertThat(SpringBootJdk21Application.class).isNotNull();
    }

    @Test
    void mainMethodExists() throws NoSuchMethodException {
        // Verify main method exists without running it
        assertThat(SpringBootJdk21Application.class.getMethod("main", String[].class))
                .isNotNull();
    }

}
