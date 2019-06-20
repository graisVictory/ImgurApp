package com.graisvictory.imgur.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.graisvictory.imgur.R;
import com.graisvictory.imgur.ui.adapter.ImagesAdapter;
import com.graisvictory.imgur.ui.common.BaseActivity;
import com.graisvictory.imgur.viewmodel.common.DataState;
import com.graisvictory.imgur.viewmodel.images.Image;
import com.graisvictory.imgur.viewmodel.images.ImageListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesActivity extends BaseActivity<ImageListViewModel> {

    @BindView(R.id.imagesList)
    RecyclerView imagesList;
    @BindView(R.id.progress)
    SpinKitView progress;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private ImagesAdapter adapter;

    @Override
    protected Class<ImageListViewModel> getViewModelClass() {
        return ImageListViewModel.class;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        ButterKnife.bind(this);

        adapter = new ImagesAdapter();
        adapter.setItems(viewModel.getLatestItems());

        setupSwipeRefresh();
        setupRecyclerView();
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refresh();
            adapter.clear();
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        imagesList.setLayoutManager(manager);
        imagesList.setAdapter(adapter);

        imagesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    loadNextPageIfNeeded(manager);
                }
            }
        });
    }

    private void loadNextPageIfNeeded(LinearLayoutManager layoutManager) {
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
            viewModel.requestNextItems();
        }
    }

    @Override
    protected void subscribeOnViewModel() {
        super.subscribeOnViewModel();
        viewModel.getPageLiveData()
                .observe(this, this::handleImageData);
    }

    private void handleImageData(DataState<List<Image>> dataState) {
        if (dataState.isSuccess()) {
            adapter.addItems(dataState.getData());
        }
        boolean showRefresh = dataState.isLoading() && viewModel.getCurrentPage() == 0;
        boolean showBottomLoader = dataState.isLoading() && viewModel.getCurrentPage() > 0;

        swipeRefresh.setRefreshing(showRefresh);

        int progressVisibility = showBottomLoader ? View.VISIBLE : View.GONE;
        progress.setVisibility(progressVisibility);
    }

}