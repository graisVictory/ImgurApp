package com.graisvictory.imgur.viewmodel.common;

public final class LocalizedError {

    private int text;
    private String argument;

    public LocalizedError(int text, String argument) {
        this.text = text;
        this.argument = argument;
    }

    public int getText() {
        return text;
    }

    public String getArgument() {
        return argument;
    }
}
