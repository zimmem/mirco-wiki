package com.zimmem.gae.wiki.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zimmem.gae.wiki.repository.WikiPageRepository;

@Controller
public class Index {

    @Autowired
    private WikiPageRepository repository;

    @RequestMapping("/")
    public String index(Map<String, Object> models) {
        models.put("wikiPages", repository.listRootWikiPages());
        return "index";

    }
}
