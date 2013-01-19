package com.zimmem.gae.wiki.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zimmem.gae.wiki.model.WikiPage;
import com.zimmem.gae.wiki.repository.WikiPageRepository;

public class WikiPageRepositoryTest extends RepositoryTestBase {

    @Autowired
    WikiPageRepository wikiPageRepository;

    @Test
    public void test() {
        System.out.println(wikiPageRepository.count());
    }

    @Test
    public void testTagIds() {
        WikiPage page = new WikiPage();
        Set<Long> set = new HashSet<Long>();
        set.add(1L);
        set.add(2L);
        Set<String> strset = new LinkedHashSet<String>();
        strset.add("abc");
        strset.add("cde");
        page.setTags(strset);
        page = wikiPageRepository.save(page);
        page = wikiPageRepository.findOne(page.getId());
        assertEquals(2, page.getTags().size());
        System.out.println(page.getTags());
        List<WikiPage> listWikiPagesByTag = wikiPageRepository.listWikiPagesByTag("abc");
        assertFalse(listWikiPagesByTag.isEmpty());
    }
}
