package com.zimmem.gae.wiki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.repository.WikiPageRepository;

@Controller
public class ArticleController {

    @Autowired
    private WikiPageRepository wikiPageRepository;

    @RequestMapping("/articles")
    public String firstPageArticles(Model model) {
        return articleList(0, 20, model);
    }

    @RequestMapping("/articles/page-{page}")
    public String articleList(@PathVariable Integer page,
                              @MatrixVariable(value = "size", required = false) Integer size, Model model) {
        size = size == null || size <= 0 ? 20 : size;
        page = page == null || page <= 0 ? 0 : page - 1;
        Pageable pageable = new PageRequest(page, size, Direction.DESC, "modifiedTime");
        Page<WikiPage> articlePage = wikiPageRepository.listRootWikiPages(pageable);
        model.addAttribute("articles", articlePage.getContent());
        model.addAttribute("page", articlePage);
        return "articles";

    }

    @RequestMapping("/articles/id-{id}")
    public String articleDetail(@PathVariable long id, Model model) {
        model.addAttribute("article", wikiPageRepository.findOne(id));
        List<WikiPage> children = wikiPageRepository.listWikiPagesByParentId(id);
        model.addAttribute("children", children);
        return "article";
    }

}
