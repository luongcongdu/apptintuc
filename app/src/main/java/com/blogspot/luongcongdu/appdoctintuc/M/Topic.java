package com.blogspot.luongcongdu.appdoctintuc.M;

/**
 * Created by Admin on 11/20/2017.
 */

public class Topic {
    private String id;
    private String name;
    private int image;
    private String link;

    public Topic(String id, String name, int image, String link) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
