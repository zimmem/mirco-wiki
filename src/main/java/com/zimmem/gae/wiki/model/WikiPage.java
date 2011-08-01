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
public class WikiPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;

    private String title;
    @Basic
    private User   creater;
    @Basic
    private User   editor;

    private Date   createTime;

    private Date   modifiedTime;

    private Long   parentId;

    private Long   revision;

    @Basic
    private Text   wikiText;

    @Basic
    private Text   htmlText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    public Text getWikiText() {
        return wikiText;
    }

    public void setWikiText(Text wikiText) {
        this.wikiText = wikiText;
    }

    public String getWiki() {
        return wikiText == null ? "" : wikiText.getValue();
    }

    public void setWiki(String wiki) {
        wikiText = wiki == null ? null : new Text(wiki);
    }

    public Text getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(Text htmlText) {
        this.htmlText = htmlText;
    }

    public String getHtml() {
        return htmlText == null ? "" : htmlText.getValue();
    }

    public void setHtml(String html) {
        htmlText = html == null ? null : new Text(html);
    }
}
