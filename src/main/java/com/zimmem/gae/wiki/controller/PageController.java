package com.zimmem.gae.wiki.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.users.UserServiceFactory;
import com.petebevin.markdown.MarkdownProcessor;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.service.WikiPageService;

@Controller
public class PageController {

    @Autowired
    private WikiPageService wikpagePageService;

    @ResponseBody
    @RequestMapping(value = "/post_page", method = RequestMethod.POST)
    public WikiPage postPage(WikiPage wikiPage) throws IOException {
        MarkdownProcessor process = new MarkdownProcessor();
        wikiPage.setHtml(process.markdown(wikiPage.getWiki()));
        wikpagePageService.saveWikiPage(wikiPage);
        return wikiPage;
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
    
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public String preview(WikiPage wikiPage, Map<String, Object> model){
        MarkdownProcessor process = new MarkdownProcessor();
        wikiPage.setHtml(process.markdown(wikiPage.getWiki()));
        wikiPage.setCreater(UserServiceFactory.getUserService().getCurrentUser());
        wikiPage.setEditor(UserServiceFactory.getUserService().getCurrentUser());
        model.put("wikiPage", wikiPage);
        return "/page";
    }

}
