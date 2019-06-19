package com.graisvictory.imgur.data;

import com.graisvictory.imgur.domain.ApiResponse;
import com.graisvictory.imgur.domain.post.ImgurPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostsService {

    @GET("gallery/hot/time/{page}.json")
    Call<ApiResponse<List<ImgurPost>>> getNextPage(@Path("page") int page);

}