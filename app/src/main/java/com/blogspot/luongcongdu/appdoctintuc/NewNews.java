package com.blogspot.luongcongdu.appdoctintuc;

/**
 * Created by Admin on 11/20/2017.
 */

public class NewNews {
    private String title;
    private String img;
    private String link;

    public NewNews(String title, String img, String link) {
        this.title = title;
        this.img = img;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
