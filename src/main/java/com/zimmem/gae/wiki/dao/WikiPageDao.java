package com.zimmem.gae.wiki.dao;

import java.util.List;

import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.model.WikiRevision;

public interface WikiPageDao {

    WikiPage findWikiPage(Long id);

    void insertWikiPage(WikiPage wikipage);

    void editWikiPage(WikiPage wikipage);

    List<WikiPage> listWikiPages(Long parentId);

    List<WikiPage> listRootWikiPages();

    WikiRevision findWikiRevision(Long pageId, int version);
}
