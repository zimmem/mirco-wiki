package com.zimmem.gae.wiki.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.petebevin.markdown.MarkdownProcessor;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.service.WikiPageService;

@Controller
public class PageManager {

    @Autowired
    private WikiPageService wikpagePageService;

    @RequestMapping(value = "/post_page", method = RequestMethod.POST)
    public String postPage(WikiPage wikiPage) throws  IOException {
        MarkdownProcessor process = new MarkdownProcessor();
        StringWriter writer = new StringWriter();
        wikiPage.setHtml(process.markdown(wikiPage.getWiki()));
        wikpagePageService.saveWikiPage(wikiPage);
        return "redirect:/page?id=" + wikiPage.getId();
    }

    @RequestMapping(value = "/edit_page", method = RequestMethod.GET)
    public String editPage(Map<String, Object> model, @RequestParam("id") long id) {
        WikiPage page = wikpagePageService.findWikiPage(id);
        model.put("wikiPage", page);
        return "/pageForm";
    }

    @RequestMapping(value = "/create_page", method = RequestMethod.GET)
    public String createPage(Map<String, Object> model,
                             @RequestParam(value = "parentId", required = false, defaultValue = "0") long parentId) {
        WikiPage page = new WikiPage();
        page.setParentId(parentId);
        model.put("wikiPage", page);
        return "/pageForm";
    }
}
