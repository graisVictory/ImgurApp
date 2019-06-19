package com.graisvictory.imgur.data.exception;

public class BackendException extends Throwable {

    private int code;

    public BackendException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
