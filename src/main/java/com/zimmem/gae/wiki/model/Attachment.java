package com.zimmem.gae.wiki.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.appengine.api.datastore.Text;

@Entity
public class Attachment implements Serializable {

    private static final long serialVersionUID = 3368068842918875766L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long              id;

    private Long              size;

    private Date              crateTime;

    private Text              gfsPath;

    private String            blobKey;

    private Text            gfsName;

    private String            name;

    private String            contentType;

    private String            encoding;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGfsPath() {
        return gfsPath == null ? null : gfsPath.getValue() ;
    }

    public void setGfsPath(String gfsPath) {
        this.gfsPath = gfsPath == null ? null : new Text(gfsPath);
    }

    public String getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(String blobKey) {
        this.blobKey = blobKey;
    }

    public String getGfsName() {
        return gfsName == null ? null : gfsName.getValue();
    }

    public void setGfsName(String gfsName) {
        this.gfsName = gfsName == null ? null : new Text(gfsName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
