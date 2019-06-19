package com.graisvictory.imgur.di.module;

import androidx.lifecycle.ViewModel;

import com.graisvictory.imgur.viewmodel.images.ImageListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ImageListViewModel.class)
    protected abstract ViewModel imageListViewModel(ImageListViewModel viewModel);

}
