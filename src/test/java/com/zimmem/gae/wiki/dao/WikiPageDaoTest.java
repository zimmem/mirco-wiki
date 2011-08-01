package com.zimmem.gae.wiki.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.zimmem.gae.wiki.model.WikiPage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:wiki-dal.xml" })
public class WikiPageDaoTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
	    new LocalDatastoreServiceTestConfig());

    @Autowired
    private WikiPageDao wikiPageDao;

    @Before
    public void setUp() throws Exception {
	helper.setUp();
    }

    @After
    public void tearDown() throws Exception {
	helper.tearDown();
    }

    @Test
    public void test() {
	WikiPage page = new WikiPage();
	page.setWiki("123");
	page.setHtml("123");
	wikiPageDao.insertWikiPage(page);
	WikiPage page2 = wikiPageDao.findWikiPage(page.getId());
	Assert.assertEquals(page.getWiki(), page2.getWiki());
	System.out.println(page.getWiki());
    }

    // @Test
//    public void testShouldPersistEmployee() {
//	EntityManager em = EMF.get().createEntityManager();
//	WikiPage page = new WikiPage();
//	page.setWiki(new Text("123"));
//	page.setHtml(new Text("123"));
//	em.persist(page);
//
//	WikiPage merge = em.merge(page);
//
//	WikiPage foundEmployee = em.find(WikiPage.class, merge.getId());
//
//	Assert.assertEquals(page.getWiki(), foundEmployee.getWiki());
//
//    }

//    public static class EMF {
//	private static EntityManagerFactory emfInstance = Persistence
//		.createEntityManagerFactory("wiki");
//
//	private EMF() {
//	}
//
//	public static EntityManagerFactory get() {
//	    return emfInstance;
//	}
//    }

}
