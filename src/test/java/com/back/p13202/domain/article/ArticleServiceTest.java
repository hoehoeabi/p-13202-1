package com.back.p13202.domain.article;

import com.back.p13202.domain.user.User;
import com.back.p13202.global.exception.DataNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @MockitoBean
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("새 게시물을 생성하면 repository.save가 호출되어야 한다")
    void createTest() {
        // Given
        User user = User.builder().username("user1").build();

        // When
        articleService.create("제목", "내용", user);

        // Then
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회 시 DataNotFoundException이 발생한다")
    void getByIdFailTest() {
        // Given: ID가 99인 글을 찾으면 비어있는 Optional을 리턴하도록 설정
        when(articleRepository.findById(99)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DataNotFoundException.class, () -> {
            articleService.getById(99);
        });
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("본인 글은 성공적으로 수정할 수 있다")
    void updateSuccessTest() {
        // Given
        Article article = Article.builder()
                .id(1)
                .author(User.builder().username("user1").build())
                .build();

        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));

        // When & Then
        assertDoesNotThrow(() -> {
            articleService.updateArticle(1, "수정 제목", "수정 내용");
        });
        // 보안 체크 시 1번 + 서비스 로직 내에서 1번 = 총 2번 호출됨
        verify(articleRepository, times(2)).findById(1);
    }

    @Test
    @WithMockUser(username = "otherUser")
    @DisplayName("남의 글 수정 시 AccessDeniedException이 발생한다")
    void updateForbiddenTest() {
        // Given: 작성자가 user1인 게시물
        Article article = Article.builder()
                .id(1)
                .author(User.builder().username("user1").build())
                .build();

        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));

        // When & Then: @PreAuthorize에 의해 에러가 터져야 함
        assertThrows(AccessDeniedException.class, () -> {
            articleService.updateArticle(1, "수정 제목", "수정 내용");
        });
    }

    @Test
    @WithMockUser(username = "user1")
    @DisplayName("본인 글은 성공적으로 삭제할 수 있다")
    void deleteSuccessTest() {
        // Given
        Article article = Article.builder()
                .id(1)
                .author(User.builder().username("user1").build())
                .build();

        when(articleRepository.findById(anyInt())).thenReturn(Optional.of(article));

        // When
        articleService.deleteArticle(1);

        // Then
        verify(articleRepository, times(1)).deleteById(1);
    }
}