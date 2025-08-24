package id.my.hendisantika.springbootjdk21.service;

import id.my.hendisantika.springbootjdk21.dto.SignUpDTO;
import id.my.hendisantika.springbootjdk21.entity.User;
import id.my.hendisantika.springbootjdk21.repository.UserRepository;
import id.my.hendisantika.springbootjdk21.security.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jdk21
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 24/08/25
 * Time: 08.37
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final String COOKIE_NAME = "SESSIONID";

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User signUp(SignUpDTO request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRoleId(RoleEnum.SIGNED_OUT.getRoleId());
        user.setRoleName(RoleEnum.SIGNED_OUT.getRoleName());
        return userRepository.save(user);
    }
}
