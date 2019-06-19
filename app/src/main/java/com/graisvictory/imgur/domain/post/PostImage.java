package com.graisvictory.imgur.domain.post;

import com.google.gson.annotations.SerializedName;

public final class PostImage {

    @SerializedName("id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("datetime")
    private long datetime;
    @SerializedName("link")
    private String link;

    public PostImage(String id, String type, long datetime, String link) {
        this.id = id;
        this.type = type;
        this.datetime = datetime;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public long getDatetime() {
        return datetime;
    }

    public String getLink() {
        return link;
    }
}