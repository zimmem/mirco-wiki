package com.zimmem.gae.wiki.dao;

import java.util.Date;
import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.model.WikiRevision;

public class WikiPageDaoImpl extends JpaDaoSupport implements WikiPageDao {

    @Override
    public void insertWikiPage(WikiPage wikipage) {
        Date now = new Date();
        wikipage.setModifiedTime(now);
        wikipage.setCreateTime(now);
        getJpaTemplate().persist(wikipage);

    }

    @Override
    public void editWikiPage(WikiPage wikipage) {
        Date now = new Date();
        wikipage.setModifiedTime(now);
        getJpaTemplate().merge(wikipage);

    }

    @Override
    public WikiPage findWikiPage(Long id) {
        return getJpaTemplate().find(WikiPage.class, id);

    }

    @Override
    public List<WikiPage> listWikiPages(Long parentId) {
        return getJpaTemplate().find("select w from WikiPage w where w.parentId = :1", parentId);
    }

    @Override
    public List<WikiPage> listRootWikiPages() {
        return getJpaTemplate().find("select w from WikiPage w where w.parentId = 0  or w.parentId is NULL");
    }

    public WikiRevision findWikiRevision(Long pageId, int version) {
        List<WikiRevision> list = getJpaTemplate().find("select r from WikiRevision r where r.pageId = :1 and r.version = :2",
                                                        pageId, version);
        return list.isEmpty() ? null : list.get(0);
    }
}
