package com.zimmem.gae.wiki.dao;

import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.zimmem.gae.wiki.model.WikiRevision;

public class WikiRevisionDaoImpl extends JpaDaoSupport implements WikiRevisionDao {

    @Override
    public void insertWikiRevision(WikiRevision revision) {
        getJpaTemplate().persist(revision);

    }

}
