package com.zimmem.gae.wiki.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zimmem.gae.wiki.model.WikiPage;

public interface WikiPageRepository extends PagingAndSortingRepository<WikiPage, Long> {

    @Query("select w from WikiPage w where w.parentId = 0  or w.parentId is NULL order by w.modifiedTime desc")
    List<WikiPage> listRootWikiPages();

    @Query("select w from WikiPage w where w.parentId = 0  or w.parentId is NULL")
    Page<WikiPage> listRootWikiPages(Pageable pageable);

    @Query("select w from WikiPage w where w.parentId = ?1")
    List<WikiPage> listWikiPagesByParentId(long parentId);
    
    @Query("select w from WikiPage w where ?1 MEMBER OF w.tags ")
    List<WikiPage> listWikiPagesByTag(String tag);
}
