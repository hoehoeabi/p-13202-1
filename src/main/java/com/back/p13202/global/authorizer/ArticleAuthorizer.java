package com.back.p13202.global.authorizer;

import com.back.p13202.domain.article.Article;
import com.back.p13202.domain.article.ArticleRepository;
import com.back.p13202.domain.article.ArticleService;
import com.back.p13202.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("articleAuthorizer")
@RequiredArgsConstructor
public class ArticleAuthorizer {

    private final ArticleRepository articleRepository;

    public boolean isAuthor(Integer articleId, String username) {
        if (username == null) return false;

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new DataNotFoundException("게시물이 없다이"));

        if (article.getAuthor() == null) return false;
        return article.getAuthor().getUsername().equals(username);
    }
}
