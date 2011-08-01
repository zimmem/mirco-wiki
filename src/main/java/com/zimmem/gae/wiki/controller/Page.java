package com.zimmem.gae.wiki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zimmem.gae.wiki.dao.WikiPageDao;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.service.WikiPageService;

@Controller
public class Page {

    @Autowired
    private WikiPageDao     wikiPageDao;

    @Autowired
    private WikiPageService wikiPageService;

    @RequestMapping("/page")
    public void show(Map<String, Object> model, @RequestParam("id") long id) {
        WikiPage wikiPage = wikiPageDao.findWikiPage(id);
        model.put("wikiPage", wikiPage);
        List<WikiPage> children = wikiPageService.listWikiPages(id);
        model.put("children", children);
    }

}
