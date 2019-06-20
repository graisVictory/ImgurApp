package com.graisvictory.imgur.viewmodel.images;

public final class Image {
    private String id;
    private String link;
    private String author;
    private String title;

    Image(String id, String link, String author, String title) {
        this.id = id;
        this.link = link;
        this.author = author;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
