package com.back.p13202.domain.user;

import com.back.p13202.global.exception.DuplicateUserName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("로그인 페이지가 정상적으로 노출되어야 한다")
    void loginPageTest() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("hideSearch", true))
                .andExpect(view().name("user/login_form"));
    }

    @Test
    @DisplayName("회원가입 성공 시 로그인 페이지로 리다이렉트 된다")
    void signupSuccessTest() throws Exception {
        mockMvc.perform(post("/user/signup")
                        .param("username", "newuser")
                        .param("password", "12341234")
                        .param("passwordConfirm", "12341234")
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    @DisplayName("비밀번호와 확인용 비밀번호가 다르면 에러와 함께 폼으로 돌아온다")
    void signupPasswordMismatchTest() throws Exception {
        mockMvc.perform(post("/user/signup")
                        .param("username", "testuser")
                        .param("password", "12341234")
                        .param("passwordConfirm", "different")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/signup_form"))
                .andExpect(model().hasErrors()); // BindingResult에 에러가 담겼는지 확인
    }

    @Test
    @DisplayName("중복된 아이디로 가입 시 에러 메시지를 보여준다")
    void signupDuplicateUserTest() throws Exception {
        // UserService.signUp 호출 시 중복 예외가 터지도록 Mock 설정
        doThrow(new DuplicateUserName("중복이다이"))
                .when(userService).signUp(anyString(), anyString(), anyString());

        mockMvc.perform(post("/user/signup")
                        .param("username", "existingUser")
                        .param("password", "12341234")
                        .param("passwordConfirm", "12341234")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/signup_form"))
                .andExpect(model().attributeHasFieldErrors("signupForm", "username"));
    }
}