package com.graisvictory.imgur.di;

import com.graisvictory.imgur.data.repository.PostsRepositoryTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { TestRestModule.class })
public abstract class RepositoryComponent {

    public abstract void inject(PostsRepositoryTest test);

}
