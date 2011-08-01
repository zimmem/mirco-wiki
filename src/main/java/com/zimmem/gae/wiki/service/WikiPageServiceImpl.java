package com.zimmem.gae.wiki.service;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.zimmem.gae.wiki.dao.WikiPageDao;
import com.zimmem.gae.wiki.model.WikiPage;

public class WikiPageServiceImpl implements WikiPageService {

    private WikiPageDao wikiPageDao;

    private UserService userService = UserServiceFactory.getUserService();

    @Override
    public void saveWikiPage(WikiPage wikiPage) {
        Date now = new Date();

        if (wikiPage.getId() != null && wikiPage.getId() >= 0) {
            WikiPage old = findWikiPage(wikiPage.getId());
            if (old != null) {
                old.setEditor(userService.getCurrentUser());
                old.setModifiedTime(now);
                old.setTitle(wikiPage.getTitle());
                old.setWiki(wikiPage.getWiki());
                old.setHtml(wikiPage.getHtml());
                wikiPageDao.editWikiPage(old);
            } else {
                wikiPage.setCreater(userService.getCurrentUser());
                wikiPage.setCreateTime(now);
                wikiPageDao.saveWikiPage(wikiPage);
            }
        } else {

            wikiPage.setCreater(userService.getCurrentUser());
            wikiPage.setCreateTime(now);
            wikiPageDao.saveWikiPage(wikiPage);
        }

    }

    public WikiPageDao getWikiPageDao() {
        return wikiPageDao;
    }

    public void setWikiPageDao(WikiPageDao wikiPageDao) {
        this.wikiPageDao = wikiPageDao;
    }

    @Override
    public WikiPage findWikiPage(Long id) {
        return wikiPageDao.findWikiPage(id);
    }

    @Override
    public List<WikiPage> listRootWikiPages() {
        return wikiPageDao.listRootWikiPages();
    }

    @Override
    public List<WikiPage> listWikiPages(Long parentId) {
        return wikiPageDao.listWikiPages(parentId);
    }
}
