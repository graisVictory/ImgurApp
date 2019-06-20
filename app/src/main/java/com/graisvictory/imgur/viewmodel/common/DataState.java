package com.graisvictory.imgur.viewmodel.common;

public final class DataState<T> {

    public enum State {
        LOADING,
        SUCCESS,
        FAIL
    }

    private State state;
    private Throwable error;
    private T data;

    private DataState(State state, T data, Throwable error) {
        this.state = state;
        this.error = error;
        this.data = data;
    }

    public static <T> DataState<T> createLoadingState() {
        return new DataState<>(State.LOADING, null, null);
    }

    public static <T> DataState<T> createErrorState(Throwable error) {
        return new DataState<>(State.FAIL, null, error);
    }

    public static <T> DataState<T> createSuccessState(T data) {
        return new DataState<>(State.SUCCESS, data, null);
    }

    public State getState() {
        return state;
    }

    public boolean isSuccess() {
        return state == State.SUCCESS;
    }

    public boolean isError() {
        return state == State.FAIL;
    }

    public boolean isLoading() {
        return state == State.LOADING;
    }

    public Throwable getError() {
        return error;
    }

    public T getData() {
        return data;
    }

}