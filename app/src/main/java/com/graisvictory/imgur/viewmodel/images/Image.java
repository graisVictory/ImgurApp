package com.graisvictory.imgur.viewmodel.images;

public final class Image {
    private String id;
    private String link;
    private long datetime;
    private String author;
    private String title;

    public Image(String id, String link, long datetime, String author, String title) {
        this.id = id;
        this.link = link;
        this.datetime = datetime;
        this.author = author;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
