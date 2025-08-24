package id.my.hendisantika.springbootjdk21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleUnitTest {

    @Test
    void simpleTest() {
        String result = "Hello World";
        assertThat(result).isEqualTo("Hello World");
    }

    @Test
    void mathTest() {
        int sum = 2 + 2;
        assertThat(sum).isEqualTo(4);
    }
}