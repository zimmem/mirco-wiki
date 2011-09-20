package com.zimmem.gae.wiki.config;

public class Metadata {

    public String getTitle() {
        return getSysProp("mircowiki.metadata.title");
    }

    private String getSysProp(String key) {
        return System.getProperty(key);
    }
}
