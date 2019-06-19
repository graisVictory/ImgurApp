package com.graisvictory.imgur.di.module;

import com.google.gson.Gson;
import com.graisvictory.imgur.data.Configuration;
import com.graisvictory.imgur.data.PostsService;
import com.graisvictory.imgur.data.RequestInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RestModule {

    @Provides
    Gson provideGson() {
        return new Gson();
    }

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
    HttpLoggingInterceptor provideHttpInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    public Interceptor provideRequestInterceptor() {
        return new RequestInterceptor();
    }

    @Provides
    OkHttpClient provideOkHttpClient(Interceptor interceptor,
             HttpLoggingInterceptor logger) {
        return new OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(interceptor)
                .callTimeout(Configuration.CALL_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(Configuration.READ_TIMEOUT_SEC, TimeUnit.SECONDS)
                .connectTimeout(Configuration.CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .build();
    }
}
