package com.zimmem.gae.wiki.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppVar {
    
    public AppVar(String key, String value){
        this.key = key;
        this.value = value;
    }

    @Id
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
