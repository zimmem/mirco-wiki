package com.zimmem.gae.wiki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.repository.WikiPageRepository;

@Controller
public class Page {

    @Autowired
    private WikiPageRepository repository;


    @RequestMapping("/page")
    public void main(Map<String, Object> model, @RequestParam("id") long id) {
        WikiPage wikiPage = repository.findOne(id);
        model.put("wikiPage", wikiPage);
        List<WikiPage> children = repository.listWikiPagesByParentId(id);
        model.put("children", children);
    }

}
