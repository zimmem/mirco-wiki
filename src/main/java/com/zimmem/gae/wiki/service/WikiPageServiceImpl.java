package com.zimmem.gae.wiki.service;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.zimmem.gae.wiki.dao.WikiPageDao;
import com.zimmem.gae.wiki.dao.WikiRevisionDao;
import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.model.WikiRevision;

public class WikiPageServiceImpl implements WikiPageService {

    private WikiPageDao     wikiPageDao;

    private WikiRevisionDao wikiRevisionDao;

    private UserService     userService = UserServiceFactory.getUserService();

    @Override
    public void saveWikiPage(WikiPage wikiPage) {

        if (wikiPage.getId() == null || wikiPage.getId() < 0) {
            addWikiPage(wikiPage);
        } else {
            WikiPage old = findWikiPage(wikiPage.getId());
            if (old != null) {
                modifiedWikiPage(old, wikiPage);
            } else {
                addWikiPage(wikiPage);
            }
        }

    }

    private void modifiedWikiPage(WikiPage old, WikiPage theNew) {

        WikiRevision revision = new WikiRevision();
        revision.setAuthor(old.getEditor());
        revision.setHtml(old.getHtml());
        revision.setModifiedTime(old.getModifiedTime());
        revision.setPageId(old.getId());
        revision.setVersion(old.getVersion() == null ? 1:old.getVersion()  );
        revision.setWiki(old.getWiki());

        old.setEditor(userService.getCurrentUser());
        old.setTitle(theNew.getTitle());
        old.setWiki(theNew.getWiki());
        old.setHtml(theNew.getHtml());
        old.setVersion(old.getVersion() == null ? 2:old.getVersion() + 1);
        old.setModifiedTime(new Date());
        old.addRevision(revision);
        wikiPageDao.editWikiPage(old);
        // wikiRevisionDao.insertWikiRevision(revision);

    }

    private void addWikiPage(WikiPage wikiPage) {
        Date now = new Date();
        wikiPage.setCreater(userService.getCurrentUser());
        wikiPage.setEditor(userService.getCurrentUser());
        wikiPage.setCreateTime(now);
        wikiPage.setModifiedTime(now);
        wikiPage.setVersion(1);
        wikiPageDao.insertWikiPage(wikiPage);

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

    public WikiRevisionDao getWikiRevisionDao() {
        return wikiRevisionDao;
    }

    public void setWikiRevisionDao(WikiRevisionDao wikiRevisionDao) {
        this.wikiRevisionDao = wikiRevisionDao;
    }

    @Override
    public WikiRevision findWikiRevision(Long pageId, int version) {
        return wikiPageDao.findWikiRevision(pageId, version);
    }

}
