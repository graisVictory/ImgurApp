package com.graisvictory.imgur.domain;

import com.google.gson.annotations.SerializedName;

public final class ApiResponse<T> {

    @SerializedName("data")
    private T data;
    @SerializedName("success")
    private boolean success;
    @SerializedName("status")
    private int statusCode;

    public ApiResponse(T data, boolean success, int statusCode) {
        this.data = data;
        this.success = success;
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
