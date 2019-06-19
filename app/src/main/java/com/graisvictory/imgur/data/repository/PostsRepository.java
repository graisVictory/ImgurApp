package com.graisvictory.imgur.data.repository;

import androidx.core.util.Consumer;

import com.graisvictory.imgur.data.PostsService;
import com.graisvictory.imgur.domain.post.ImgurPost;

import java.util.List;

import javax.inject.Inject;

public class PostsRepository extends BaseRepository {

    private PostsService postsService;

    @Inject
    PostsRepository(PostsService postsService) {
        this.postsService = postsService;
    }

    public void loadPage(int page, Consumer<List<ImgurPost>> onSuccess,
                         Consumer<Throwable> onError) {
        performCall(postsService.getNextPage(page), onSuccess, onError);
    }

}

