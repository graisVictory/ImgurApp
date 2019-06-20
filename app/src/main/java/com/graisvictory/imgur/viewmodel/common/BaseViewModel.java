package com.graisvictory.imgur.viewmodel.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.graisvictory.imgur.R;
import com.graisvictory.imgur.data.exception.BackendException;
import com.graisvictory.imgur.data.exception.NoInternetException;

public class BaseViewModel extends ViewModel {

    private MutableLiveData<LocalizedError> error = new MutableLiveData<>();

    public LiveData<LocalizedError> getErrorLiveData() {
        return error;
    }

    protected void postProperError(Throwable cause) {
        int text = R.string.unknown_error;
        String argument = null;
        if (cause instanceof NoInternetException) {
            text = R.string.check_internet_connection;
        } else if (cause instanceof BackendException) {
            BackendException exception = (BackendException) cause;
            text = R.string.something_went_wrong;
            argument = String.valueOf(exception.getCode());
        }
        error.postValue(new LocalizedError(text, argument));
    }
}
