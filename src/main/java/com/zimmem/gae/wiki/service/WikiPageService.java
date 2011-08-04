package com.zimmem.gae.wiki.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.model.WikiRevision;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface WikiPageService {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void saveWikiPage(WikiPage wikiPage);

    WikiPage findWikiPage(Long id);

    List<WikiPage> listRootWikiPages();

    List<WikiPage> listWikiPages(Long parentId);

    public WikiRevision findWikiRevision(Long pageId, int version);
}
