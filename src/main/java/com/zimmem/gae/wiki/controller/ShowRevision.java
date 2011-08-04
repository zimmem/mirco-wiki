package com.zimmem.gae.wiki.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zimmem.gae.wiki.model.WikiRevision;
import com.zimmem.gae.wiki.service.WikiPageService;

@Controller
public class ShowRevision {

    @Autowired
    private WikiPageService wikiPageService;

    @RequestMapping("/show_revision")
    public void show(@RequestParam("pageId") long pageId, @RequestParam("version") int version,
                     Map<String, Object> model) {
        WikiRevision revision = wikiPageService.findWikiRevision(pageId, version);
        model.put("revision", revision);
    }
}
