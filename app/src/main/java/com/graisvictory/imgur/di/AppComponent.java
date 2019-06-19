package com.graisvictory.imgur.di;

import com.graisvictory.imgur.ImgurApp;
import com.graisvictory.imgur.di.module.ActivityBuildersModule;
import com.graisvictory.imgur.di.module.RestModule;
import com.graisvictory.imgur.di.module.ViewModelsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { RestModule.class, ViewModelsModule.class, ActivityBuildersModule.class })
public interface AppComponent {

    void inject(ImgurApp app);

}
