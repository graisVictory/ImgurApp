package com.graisvictory.imgur.data;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {

    private static final String AUTH_HEADER = "Authorization";
    private static final String CLIENT_ID_PREFIX = "Client-ID ";

    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader(AUTH_HEADER, CLIENT_ID_PREFIX + Configuration.CLIENT_ID)
                .build();
        return chain.proceed(request);
    }

}