package com.back.p13202.domain.user;

import com.back.p13202.global.exception.DuplicateUserName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 시 비밀번호가 암호화되어 저장되어야 한다")
    void signUpSuccessTest() {
        // Given: 중복된 아이디가 없는 상황
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // When
        User savedUser = userService.signUp("testuser", "rawPassword", "rawPassword");

        // Then
        assertNotNull(savedUser);
        // 저장 시도 시 암호화된 비밀번호가 들어갔는지 확인
        // rawPassword가 그대로 저장되면 테스트 실패
        assertNotEquals("rawPassword", savedUser.getPassword());
        assertTrue(passwordEncoder.matches("rawPassword", savedUser.getPassword()));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("이미 존재하는 아이디로 가입하면 DuplicateUserName 예외가 발생한다")
    void signUpDuplicateFailTest() {
        // Given: 이미 존재하는 유저가 있다고 가정
        User existingUser = User.builder().username("oldUser").build();
        when(userRepository.findByUsername("oldUser")).thenReturn(Optional.of(existingUser));

        // When & Then
        assertThrows(DuplicateUserName.class, () -> {
            userService.signUp("oldUser", "password", "password");
        });

        // 예외가 터졌으므로 save는 호출되면 안 됨
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("아이디로 유저를 조회할 때 없으면 DataNotFoundException이 발생한다")
    void getUserFailTest() {
        // Given
        when(userRepository.findByUsername("none")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(com.back.p13202.global.exception.DataNotFoundException.class, () -> {
            userService.getUser("none");
        });
    }
}