package com.graisvictory.imgur.di.module;

import com.graisvictory.imgur.ui.ImagesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    public abstract ImagesActivity contributeMainActivity();

}
