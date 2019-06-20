package com.graisvictory.imgur.data.repository;

import androidx.core.util.Consumer;

import com.graisvictory.imgur.data.backend.PostsService;
import com.graisvictory.imgur.data.exception.BackendException;
import com.graisvictory.imgur.data.exception.NoInternetException;
import com.graisvictory.imgur.di.DaggerRepositoryComponent;
import com.graisvictory.imgur.domain.ApiResponse;
import com.graisvictory.imgur.domain.post.ImgurPost;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class PostsRepositoryTest {

    @Inject
    PostsService postsService;

    @Inject
    PostsRepository postsRepository;

    @Before
    public void init() {
        DaggerRepositoryComponent.create()
                .inject(this);
    }

    @Test
    public void requestPostsPage_SuccessResponse_SuccessCallbackCalled() {
        Call<ApiResponse<List<ImgurPost>>> call = Mockito.mock(Call.class);

        List<ImgurPost> posts = new ArrayList<>();
        posts.add(new ImgurPost("1", "Some title", "Author", 0, new ArrayList<>()));
        ApiResponse<List<ImgurPost>> apiResponse = new ApiResponse<>(posts, true, 200);
        Response<ApiResponse<List<ImgurPost>>> response = Response.success(apiResponse);

        Mockito.doAnswer(invocation -> {
            Callback<ApiResponse<List<ImgurPost>>> callback = invocation.getArgument(0);

            callback.onResponse(call, response);

            return null;
        })
                .when(call)
                .enqueue(ArgumentMatchers.any(Callback.class));

        Mockito.when(postsService.getNextPage(ArgumentMatchers.anyInt()))
                .thenReturn(call);

        Consumer<List<ImgurPost>> success = Mockito.mock(Consumer.class);
        Consumer<Throwable> failure = Mockito.mock(Consumer.class);
        postsRepository.loadPage(0, success, failure);
        Mockito.verify(success, Mockito.times(1)).accept(posts);
    }

    @Test
    public void requestPostsPage_FailedResponse_FailCallbackCalled() {
        Call<ApiResponse<List<ImgurPost>>> call = Mockito.mock(Call.class);

        Throwable error = new BackendException(404);

        Mockito.doAnswer(invocation -> {
            Callback<ApiResponse<List<ImgurPost>>> callback = invocation.getArgument(0);

            callback.onFailure(call, error);

            return null;
        })
                .when(call)
                .enqueue(ArgumentMatchers.any(Callback.class));

        Mockito.when(postsService.getNextPage(ArgumentMatchers.anyInt()))
                .thenReturn(call);

        Consumer<List<ImgurPost>> success = Mockito.mock(Consumer.class);
        Consumer<Throwable> failure = Mockito.mock(Consumer.class);
        postsRepository.loadPage(0, success, failure);
        Mockito.verify(failure, Mockito.times(1)).accept(error);
    }

    @Test
    public void requestPostsPage_IOException_NoInternetExceptionPassed() {
        Call<ApiResponse<List<ImgurPost>>> call = Mockito.mock(Call.class);

        Throwable error = new IOException();

        Mockito.doAnswer(invocation -> {
            Callback<ApiResponse<List<ImgurPost>>> callback = invocation.getArgument(0);

            callback.onFailure(call, error);

            return null;
        })
                .when(call)
                .enqueue(ArgumentMatchers.any(Callback.class));

        Mockito.when(postsService.getNextPage(ArgumentMatchers.anyInt()))
                .thenReturn(call);

        Consumer<List<ImgurPost>> success = Mockito.mock(Consumer.class);
        Consumer<Throwable> failure = Mockito.mock(Consumer.class);
        postsRepository.loadPage(0, success, failure);
        Mockito.verify(failure, Mockito.times(1))
                .accept(ArgumentMatchers.any(NoInternetException.class));
    }

    @Test
    public void requestPostsPage_ResponseWithError_FailCallbackCalled() {
        Call<ApiResponse<List<ImgurPost>>> call = Mockito.mock(Call.class);

        ResponseBody responseBody = ResponseBody.create("some body", null);
        Response<ApiResponse<List<ImgurPost>>> response = Response.error(400, responseBody);

        Mockito.doAnswer(invocation -> {
            Callback<ApiResponse<List<ImgurPost>>> callback = invocation.getArgument(0);

            callback.onResponse(call, response);

            return null;
        })
                .when(call)
                .enqueue(ArgumentMatchers.any(Callback.class));

        Mockito.when(postsService.getNextPage(ArgumentMatchers.anyInt()))
                .thenReturn(call);

        Consumer<List<ImgurPost>> success = Mockito.mock(Consumer.class);
        Consumer<Throwable> failure = Mockito.mock(Consumer.class);
        postsRepository.loadPage(0, success, failure);
        Mockito.verify(failure, Mockito.times(1))
                .accept(ArgumentMatchers.any(BackendException.class));
    }

}
