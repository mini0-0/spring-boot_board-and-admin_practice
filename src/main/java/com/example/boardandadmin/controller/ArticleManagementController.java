package com.example.boardandadmin.controller;

import com.example.boardandadmin.dto.response.ArticleResponse;
import com.example.boardandadmin.service.ArticleManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/management/articles")
@Controller
public class ArticleManagementController {

    private final ArticleManagementService articleManagementService;

    @ModelAttribute("requestURI")
    public String requestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }


    @GetMapping
    public String articles(Model model) {
        model.addAttribute(
                "articles",
                articleManagementService.getArticles().stream().map(ArticleResponse::withoutContent).toList()
        );

        return "management/articles";
    }

    @ResponseBody
    @GetMapping("/{articleId}")
    public ArticleResponse article(@PathVariable Long articleId) {
        return ArticleResponse.withContent(articleManagementService.getArticle(articleId));
    }

    @PostMapping("/{articleId}")
    public String deleteArticle(@PathVariable Long articleId) {
        articleManagementService.deleteArticle(articleId);

        return "redirect:/management/articles";
    }
}
