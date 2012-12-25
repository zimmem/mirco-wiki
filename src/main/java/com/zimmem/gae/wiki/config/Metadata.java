package com.zimmem.gae.wiki.config;

public class Metadata {

    public String getTitle() {
        return getSysProp("mircowiki.metadata.title");
    }

    public String getSysProp(String key) {
        return System.getProperty(key);
    }
}
