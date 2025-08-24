package id.my.hendisantika.springbootjdk21;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("ci")
public class CiTestConfiguration {
    // Configuration for CI environment - uses external MySQL service
    // No Testcontainers needed as MySQL is provided by GitHub Actions services
}