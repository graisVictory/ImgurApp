package com.graisvictory.imgur.data.repository;

import androidx.core.util.Consumer;

import com.graisvictory.imgur.data.exception.BackendException;
import com.graisvictory.imgur.domain.ApiResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class BaseRepository {

    private List<Call> pendingRequests = new ArrayList<>();

    <T> void performCall(Call<ApiResponse<T>> call, Consumer<T> onSuccess,
                                   Consumer<Throwable> onError) {
        addRestCallToPending(call);
        call.enqueue(new RestCallback<>(onSuccess, onError));
    }

    private void addRestCallToPending(Call<?> call) {
        pendingRequests.add(call);
    }

    public void cancelPendingRequests() {
        for (int i = 0; i < pendingRequests.size(); i++) {
            pendingRequests.get(i).cancel();
        }
    }

    protected static class RestCallback<T> implements Callback<ApiResponse<T>> {

        private Consumer<T> onSuccess;
        private Consumer<Throwable> onError;

        RestCallback(Consumer<T> onSuccess, Consumer<Throwable> onError) {
            this.onSuccess = onSuccess;
            this.onError = onError;
        }

        @Override
        public void onResponse(@NotNull Call<ApiResponse<T>> call,
                               @NotNull Response<ApiResponse<T>> response) {
            if (response.isSuccessful()) {
                ApiResponse<T> body = response.body();

                if (body == null) {
                    onSuccess.accept(null);
                } else if (body.isSuccess()) {
                    onSuccess.accept(body.getData());
                } else {
                    BackendException exception = new BackendException(body.getStatusCode());
                    onError.accept(exception);
                }
            } else {
                onError.accept(new BackendException(response.code()));
            }
        }

        @Override
        public void onFailure(@NotNull Call<ApiResponse<T>> call, @NotNull Throwable t) {
            onError.accept(t);
        }

    }

}
