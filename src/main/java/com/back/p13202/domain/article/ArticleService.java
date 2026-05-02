package com.back.p13202.domain.article;

import com.back.p13202.global.exception.DataNotFoundException;
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
    public void create(String title, String content){
        Article article = Article.builder()
                .title(title)
                .content(content)
                .createdDate(LocalDateTime.now())
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

    @Transactional
    public void updateArticle(Integer id,String title, String content) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("게시물이 없다이"));
        article.updateArticle(title, content);
    }

    @Transactional
    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }
}

