package com.graisvictory.imgur.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.graisvictory.imgur.data.Configuration;
import com.graisvictory.imgur.data.backend.PostsService;
import com.graisvictory.imgur.data.backend.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestModule {

    @Provides
    PostsService provideImageService(Retrofit retrofit) {
        return retrofit.create(PostsService.class);
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Configuration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logger, Interceptor request) {
        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(request)
                .callTimeout(Configuration.CALL_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(Configuration.READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                .connectTimeout(Configuration.CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    HttpLoggingInterceptor provideHttpInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    Interceptor provideRequestInterceptor() {
        return new RequestInterceptor();
    }
}
