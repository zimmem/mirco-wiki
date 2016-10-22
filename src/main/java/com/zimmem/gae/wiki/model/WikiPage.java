package com.zimmem.gae.wiki.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class WikiPage implements Serializable {

    private static final long serialVersionUID = 5521589204800405879L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long              id;

    private String            title;
    @Basic
    private User              creater;
    @Basic
    private User              editor;

    private Date              createTime;

    private Date              modifiedTime;

    private Long              parentId;

    private Integer           version;

    @Basic
    private Text              wikiText;

    @Basic
    private Text              htmlText;

    private Set<String>       tags;

    //
    // @OneToMany(mappedBy = "wikiPage", cascade = CascadeType.ALL)
    // @OrderBy("version desc")
    // private List<WikiRevision> revisions;

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

    @JsonIgnore
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

    @JsonIgnore
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    // public List<WikiRevision> getRevisions() {
    // return revisions;
    // }

    // public void setRevisions(List<WikiRevision> revisions) {
    // this.revisions = revisions;
    // }
    //
    // public void addRevision(WikiRevision revision) {
    // if (revisions == null) {
    // revisions = new ArrayList<WikiRevision>();
    // }
    // revisions.add(revision);
    // }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}
