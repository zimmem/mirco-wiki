package com.zimmem.gae.wiki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zimmem.gae.wiki.dao.WikiPageDao;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.model.WikiRevision;
import com.zimmem.gae.wiki.service.WikiPageService;

@Controller
public class Page {

    @Autowired
    private WikiPageDao     wikiPageDao;

    @Autowired
    private WikiPageService wikiPageService;

    @RequestMapping("/page")
    public void main(Map<String, Object> model, @RequestParam("id") long id) {
        WikiPage wikiPage = wikiPageDao.findWikiPage(id);
        model.put("wikiPage", wikiPage);
        List<WikiPage> children = wikiPageService.listWikiPages(id);
        model.put("children", children);
    }

    @RequestMapping("/page/history")
    public String history(Map<String, Object> model, @RequestParam("id") long id) {
        WikiPage wikiPage = wikiPageDao.findWikiPage(id);
        model.put("wikiPage", wikiPage);
        return "history";
    }

    @RequestMapping("/page/history/diff")
    public String diff(@RequestParam("id") long id, @RequestParam("versions") int[] versions, Map<String, Object> model) {
        if (versions == null || versions.length < 2) {
            model.put("error", true);
        }
        WikiRevision revisionA = wikiPageService.findWikiRevision(id, versions[0]);
        WikiRevision revisionB = wikiPageService.findWikiRevision(id, versions[1]);
        model.put("preRevision", versions[0] < versions[1] ? revisionA : revisionB);
        model.put("afterRevision", versions[0] < versions[1] ? revisionB : revisionA);
        return "diff";
    }

}
