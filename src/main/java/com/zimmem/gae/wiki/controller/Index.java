package com.zimmem.gae.wiki.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zimmem.gae.wiki.service.WikiPageService;

@Controller
public class Index {

    @Autowired
    private WikiPageService wikiPageService;

    @RequestMapping("/")
    public String index(Map<String, Object> models) {
	models.put("wikiPages", wikiPageService.listRootWikiPages());
	return "index";

    }
}
