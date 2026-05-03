package com.back.p13202.domain.article;

import com.back.p13202.domain.user.User;
import com.back.p13202.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArticleService articleService;

    @MockitoBean
    private ArticleRepository articleRepository; // Authorizer가 사용하는 Repository

    @MockitoBean
    private UserService userService;

    private Article article;

    @BeforeEach
    void setUp() {
        article = Article.builder()
                .id(1)
                .title("테스트 제목")
                .content("테스트 내용")
                .author(User.builder().username("user1").build())
                .build();
    }

    @Test
    @DisplayName("목록 페이지는 로그인 없이 접근 가능하다")
    void listTest() throws Exception {
        mockMvc.perform(get("/article/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("article/article_list"));
    }

    @Test
    @DisplayName("로그인 없이 글쓰기 접근 시 로그인 페이지로 리다이렉트")
    void createGetAnonymousTest() throws Exception {
        mockMvc.perform(get("/article/create"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/login"));
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("본인 글 수정 페이지 접근 성공")
    void updateGetSuccessTest() throws Exception {
        // Authorizer가 DB에서 글을 찾는 과정 Mocking
        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));
        // 컨트롤러가 화면에 그릴 글을 가져오는 과정 Mocking
        when(articleService.getById(anyInt())).thenReturn(article);

        mockMvc.perform(get("/article/update").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("article/article_form"));
    }

    @Test
    @WithMockUser(username = "otherUser")
    @DisplayName("남의 글 수정 시도 시 403 리다이렉트")
    void updateGetForbiddenTest() throws Exception {
        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));

        mockMvc.perform(get("/article/update").param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/error/403"));
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("본인 글 수정 실행(POST) 성공")
    void updatePostSuccessTest() throws Exception {
        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));

        mockMvc.perform(post("/article/update")
                        .param("id", "1")
                        .param("title", "수정제목")
                        .param("content", "수정내용")
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/list"));
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("삭제 버튼을 GET으로 누르면 목록으로 리다이렉트")
    void deleteGetTest() throws Exception {
        mockMvc.perform(get("/article/delete").param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/list"));
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("본인 글 삭제(POST) 성공")
    void deletePostSuccessTest() throws Exception {
        // 서비스의 deleteArticle 이전에 @PreAuthorize가 repository.findById를 호출함
        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));

        mockMvc.perform(post("/article/delete")
                        .param("id", "1")
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/article/list"));
    }
}