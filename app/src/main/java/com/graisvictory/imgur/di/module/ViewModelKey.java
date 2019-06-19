package com.graisvictory.imgur.di.module;

import androidx.lifecycle.ViewModel;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.MapKey;

@Retention(RetentionPolicy.RUNTIME)
@MapKey
@interface ViewModelKey {

    Class<? extends ViewModel> value();

}
