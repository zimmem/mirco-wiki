package com.zimmem.gae.wiki.dao;

import java.util.List;

import com.zimmem.gae.wiki.model.WikiPage;

public interface WikiPageDao {

    WikiPage findWikiPage(Long id);

    void saveWikiPage(WikiPage wikipage);

    void editWikiPage(WikiPage wikipage);

    List<WikiPage> listWikiPages(Long parentId);

    List<WikiPage> listRootWikiPages();
}
