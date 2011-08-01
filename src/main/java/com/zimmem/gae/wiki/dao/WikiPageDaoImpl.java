package com.zimmem.gae.wiki.dao;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.zimmem.gae.wiki.model.WikiPage;

public class WikiPageDaoImpl extends JpaDaoSupport implements WikiPageDao {

    @Override
    public void saveWikiPage(WikiPage wikipage) {
        getJpaTemplate().persist(wikipage);

    }

    @Override
    public void editWikiPage(WikiPage wikipage) {
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
}
