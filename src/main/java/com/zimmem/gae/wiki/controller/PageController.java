package com.zimmem.gae.wiki.controller;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.repository.WikiPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
public class PageController {

    private UserService        userService = UserServiceFactory.getUserService();

    @Autowired
    private WikiPageRepository wikiPageRepository;

    @ResponseBody
    @RequestMapping(value = "/post_page", method = RequestMethod.POST)
    public WikiPage postPage(WikiPage wikiPage) throws IOException {
        saveWikiPage(wikiPage);
        return wikiPage;
    }

    @RequestMapping(value = "/edit_page", method = RequestMethod.GET)
    public String editPage(Map<String, Object> model, @RequestParam("id") long id) {
        WikiPage page = wikiPageRepository.findOne(id);
        model.put("wikiPage", page);
        return "/editor";
    }

    @RequestMapping(value = "/create_page", method = RequestMethod.GET)
    public String createPage(Map<String, Object> model,
                             @RequestParam(value = "parentId", required = false, defaultValue = "0") long parentId) {
        WikiPage page = new WikiPage();
        page.setParentId(parentId);
        model.put("wikiPage", page);
        return "/editor";
    }

    @RequestMapping("/fix")
    @ResponseBody
    public WikiPage fix(@RequestParam long id, @RequestParam String tags) {
        WikiPage page = wikiPageRepository.findOne(id);
        page.getTags().add(tags);
        wikiPageRepository.save(page);
        page = wikiPageRepository.findOne(id);
        return page;
    }

    private void saveWikiPage(WikiPage wikiPage) {

        if (wikiPage.getId() == null || wikiPage.getId() < 0) {
            addWikiPage(wikiPage);
        } else {
            WikiPage old = wikiPageRepository.findOne(wikiPage.getId());
            if (old != null) {
                modifiedWikiPage(old, wikiPage);
            } else {
                addWikiPage(wikiPage);
            }
        }

    }

    private void modifiedWikiPage(WikiPage old, WikiPage theNew) {

        old.setEditor(userService.getCurrentUser());
        old.setTitle(theNew.getTitle());
        old.setWiki(theNew.getWiki());
        old.setHtml(theNew.getHtml());
        old.setVersion(old.getVersion() == null ? 2 : old.getVersion() + 1);
        old.setModifiedTime(new Date());
        old.setTags(theNew.getTags());
        wikiPageRepository.save(old);

    }

    private void addWikiPage(WikiPage wikiPage) {
        Date now = new Date();
        wikiPage.setCreater(userService.getCurrentUser());
        wikiPage.setEditor(userService.getCurrentUser());
        wikiPage.setCreateTime(now);
        wikiPage.setModifiedTime(now);
        wikiPage.setVersion(1);
        System.out.println(wikiPageRepository + "-------------------");
        wikiPageRepository.save(wikiPage);
    }

}
