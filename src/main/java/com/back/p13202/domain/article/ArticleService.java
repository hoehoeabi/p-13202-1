package com.back.p13202.domain.article;

import com.back.p13202.domain.user.User;
import com.back.p13202.global.exception.DataNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void create(String title, String content, User author){
        Article article = Article.builder()
                .title(title)
                .content(content)
                .createdDate(LocalDateTime.now())
                .author(author)
                .build();

        articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Article getById(Integer id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("게시물이 없다이"));
    }

    @PreAuthorize("isAuthenticated() and @articleAuthorizer.isAuthor(#id, principal.username)")
    @Transactional
    public void updateArticle(Integer id,String title, String content) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("게시물이 없다이"));
        article.updateArticle(title, content);
    }

    @PreAuthorize("isAuthenticated() and @articleAuthorizer.isAuthor(#id, principal.username)")
    @Transactional
    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Article> searchArticles(String keyword) {
        return articleRepository.findAllByKeyword(keyword);
    }
}

