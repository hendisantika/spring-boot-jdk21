package id.my.hendisantika.springbootjdk21.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 24/08/25
 * Time: 08.35
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class SignUpDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
