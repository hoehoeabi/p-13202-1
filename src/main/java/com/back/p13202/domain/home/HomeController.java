package com.back.p13202.domain.home;

import com.back.p13202.domain.article.Article;
import com.back.p13202.domain.article.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String index(Model model) {
        List<Article> articleList = this.articleService.getAllArticles();
        model.addAttribute("articleList", articleList);
        return "article/article_list";
    }
}
