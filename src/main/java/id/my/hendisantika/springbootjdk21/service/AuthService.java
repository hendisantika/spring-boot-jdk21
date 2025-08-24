package id.my.hendisantika.springbootjdk21.service;

import id.my.hendisantika.springbootjdk21.dto.SignInDTO;
import id.my.hendisantika.springbootjdk21.dto.SignUpDTO;
import id.my.hendisantika.springbootjdk21.entity.User;
import id.my.hendisantika.springbootjdk21.repository.UserRepository;
import id.my.hendisantika.springbootjdk21.security.RoleEnum;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

    public User signIn(SignInDTO request, HttpServletResponse response) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getEncodedPassword())) {
                String sessionId = UUID.randomUUID().toString();
                Cookie cookie = new Cookie(COOKIE_NAME, sessionId);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setPath("/");
                response.addCookie(cookie);
                user.setSessionId(sessionId);
                user.setRoleId(RoleEnum.USER.getRoleId());
                user.setRoleName(RoleEnum.USER.getRoleName());
                return userRepository.save(user);
            }
        }
        return null;
    }

    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        Optional<User> userOptional = getUserFromSession(request);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSessionId(null);
            user.setRoleId(RoleEnum.SIGNED_OUT.getRoleId());
            user.setRoleName(RoleEnum.SIGNED_OUT.getRoleName());
            userRepository.save(user);
        }
    }

    public Optional<User> getUserFromSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    String sessionId = cookie.getValue();
                    return userRepository.findBySessionId(sessionId);
                }
            }
        }
        return Optional.empty();
    }
}
