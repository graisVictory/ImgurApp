package com.graisvictory.imgur.di;

import com.graisvictory.imgur.data.PostsService;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TestRestModule {

    @Provides
    @Singleton
    public PostsService provideMockPostService() {
        return Mockito.mock(PostsService.class);
    }

}
