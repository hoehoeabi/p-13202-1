package com.back.p13202.domain.article;

import com.back.p13202.domain.user.User;
import com.back.p13202.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Article> articleList;

        if (keyword != null && !keyword.trim().isEmpty()) {
            articleList = articleService.searchArticles(keyword);
        } else {
            articleList = articleService.getAllArticles();
        }

        model.addAttribute("articleList", articleList);
        model.addAttribute("keyword", keyword);
        return "article/article_list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("id", null);

        return "article/article_form";
    }

    @PostMapping("/create")
    public String create(ArticleForm articleForm, Principal principal){
        String username = principal.getName();
        User user = userService.getUser(username);
        articleService.create(articleForm.getTitle(), articleForm.getContent(),user);
        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        Article article = articleService.getById(id);
        model.addAttribute("article", article);

        return "article/article_detail";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam("id") Integer id) {
        Article article = articleService.getById(id);

        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle(article.getTitle());
        articleForm.setContent(article.getContent());

        model.addAttribute("articleForm", articleForm);
        model.addAttribute("id", id);

        return "article/article_form";
    }

    @PostMapping("/update")
    public String update(@RequestParam("id") Integer id, ArticleForm articleForm) {
        articleService.updateArticle(id, articleForm.getTitle(), articleForm.getContent());

        return "redirect:/article/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        articleService.deleteArticle(id);

        return "redirect:/article/list";
    }

}

