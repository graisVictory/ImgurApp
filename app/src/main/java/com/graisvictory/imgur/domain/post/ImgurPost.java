package com.graisvictory.imgur.domain.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImgurPost {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("account_url")
    private String author;
    @SerializedName("images_count")
    private int imagesCount;
    @SerializedName("images")
    private List<PostImage> postImages;

    public ImgurPost(String id, String title, String author, int imagesCount,
                     List<PostImage> postImages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imagesCount = imagesCount;
        this.postImages = postImages;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getImagesCount() {
        return imagesCount;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }
}
