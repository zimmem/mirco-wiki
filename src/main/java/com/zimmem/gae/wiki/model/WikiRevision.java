package com.zimmem.gae.wiki.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

@Entity
public class WikiRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pageId;
    private int  version;
    @Basic
    private Text wikiText;
    @Basic
    private Text htmlText;
    @Basic
    private User author;
    private Date modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Text getWikiText() {
        return wikiText;
    }

    public void setWikiText(Text wikiText) {
        this.wikiText = wikiText;
    }

    public Text getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(Text htmlText) {
        this.htmlText = htmlText;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getWiki() {
        return wikiText == null ? "" : wikiText.getValue();
    }

    public void setWiki(String wiki) {
        this.wikiText = wiki == null ? null : new Text(wiki);
    }

    public String getHtml() {
        return htmlText == null ? "" : htmlText.getValue();
    }

    public void setHtml(String html) {
        this.htmlText = html == null ? null : new Text(html);
    }

}
