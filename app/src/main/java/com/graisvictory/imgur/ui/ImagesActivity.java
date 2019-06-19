package com.graisvictory.imgur.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.graisvictory.imgur.R;
import com.graisvictory.imgur.ui.adapter.ImagesAdapter;
import com.graisvictory.imgur.viewmodel.images.ImageListViewModel;
import com.graisvictory.imgur.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class ImagesActivity extends AppCompatActivity {

    @BindView(R.id.imagesList)
    RecyclerView imagesList;
    @BindView(R.id.progress)
    SpinKitView progress;

    @Inject
    protected ViewModelFactory viewModelFactory;

    private ImageListViewModel viewModel;

    private ImagesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ImageListViewModel.class);
        subscribeOnViewModelEvents();

        adapter = new ImagesAdapter();

        imagesList.setLayoutManager(new LinearLayoutManager(this));
        imagesList.setAdapter(adapter);

        adapter.setItems(viewModel.getLatestItems());
    }

    private void subscribeOnViewModelEvents() {
        viewModel.getPageLiveData()
                .observe(this, dataState -> {
                    if (dataState.isSuccess()) {
                        adapter.addItems(dataState.getData());
                    }
                    int progressVisibility = dataState.isLoading() ? View.VISIBLE : View.GONE;
                    progress.setVisibility(progressVisibility);
                });
    }
}