package com.blogspot.luongcongdu.appdoctintuc.M;

import io.realm.RealmObject;

/**
 * Created by Admin on 11/30/2017.
 */

public class ArticleSaved extends RealmObject{
    private int id;
    private String content;

    public ArticleSaved() {
    }

    public ArticleSaved(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
