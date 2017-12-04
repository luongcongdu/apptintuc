package com.blogspot.luongcongdu.appdoctintuc.M;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Admin on 11/30/2017.
 */

public class ArticleSaved extends RealmObject {
    @PrimaryKey
    private int id;
    private String content;
    private String title;

    public ArticleSaved() {
    }

    public ArticleSaved(int id, String content, String title) {
        this.id = id;
        this.content = content;
        this.title = title;
    }

    public String toString() {
        return this.getTitle();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
