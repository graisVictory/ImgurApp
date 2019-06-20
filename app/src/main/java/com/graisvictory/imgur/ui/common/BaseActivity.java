package com.graisvictory.imgur.ui.common;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.graisvictory.imgur.viewmodel.ViewModelFactory;
import com.graisvictory.imgur.viewmodel.common.BaseViewModel;
import com.graisvictory.imgur.viewmodel.common.LocalizedError;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<T extends BaseViewModel> extends AppCompatActivity {

    protected T viewModel;

    @Inject
    protected ViewModelFactory viewModelFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        subscribeOnViewModel();
    }

    protected abstract Class<T> getViewModelClass();

    protected void subscribeOnViewModel() {
        viewModel.getErrorLiveData().observe(this, this::handleLocalizedError);
    }

    private void handleLocalizedError(LocalizedError error) {
        int textRes = error.getText();
        String argument = error.getArgument();
        String message;
        if (argument != null) {
            message = getString(textRes, argument);
        } else {
            message = getString(textRes);
        }
        showMessage(message);
    }

    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
