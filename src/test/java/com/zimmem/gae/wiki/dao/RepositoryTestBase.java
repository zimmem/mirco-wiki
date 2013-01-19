package com.zimmem.gae.wiki.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:wiki-dal.xml" })
public class RepositoryTestBase {

    private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(
                                                                                    new LocalDatastoreServiceTestConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        helper.setUp();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        helper.tearDown();
    }
}
