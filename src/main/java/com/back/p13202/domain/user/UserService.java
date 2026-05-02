package com.back.p13202.domain.user;

import com.back.p13202.global.exception.DuplicateUserName;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User signUp(String username, String password,String passwordConfirm) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicateUserName("이미 존재하는 아이디다이!");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        this.userRepository.save(user);
        return user;
    }
}
