package com.graisvictory.imgur.viewmodel.images;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.graisvictory.imgur.data.repository.PostsRepository;
import com.graisvictory.imgur.domain.post.ImgurPost;
import com.graisvictory.imgur.domain.post.PostImage;
import com.graisvictory.imgur.viewmodel.common.BaseViewModel;
import com.graisvictory.imgur.viewmodel.common.DataState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ImageListViewModel extends BaseViewModel {

    private static final String IMAGE_PREFIX = "image/";
    private static final int INITIAL_PAGE = -1;

    private PostsRepository postsRepository;
    private MutableLiveData<DataState<List<Image>>> pageLiveData = new MutableLiveData<>();
    private List<Image> loadedItems = new ArrayList<>();
    private int page = -1;
    private boolean loadingEnded;

    @Inject
    ImageListViewModel(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
        requestNextItems();
    }

    public LiveData<DataState<List<Image>>> getPageLiveData() {
        return pageLiveData;
    }

    public List<Image> getLatestItems() {
        return loadedItems;
    }

    public void refresh() {
        postsRepository.cancelPendingRequests();
        page = INITIAL_PAGE;
        loadedItems.clear();
        requestNextItems();
    }

    public int getCurrentPage() {
        return page;
    }

    public void requestNextItems() {
        if (canLoadPosts()) {
            ++page;
            pageLiveData.postValue(DataState.createLoadingState());
            postsRepository.loadPage(page, this::onPostsLoaded, this::onLoadError);
        }
    }

    private boolean canLoadPosts() {
        DataState<List<Image>> currentState = pageLiveData.getValue();
        boolean isLoadingOrLoaded = (currentState != null && currentState.isLoading())
                || loadingEnded;
        return !isLoadingOrLoaded;
    }

    private void onPostsLoaded(List<ImgurPost> posts) {
        if (posts == null || posts.isEmpty()) {
            loadingEnded = true;
            return;
        }
        List<Image> images = mapPostsToImages(posts);
        loadedItems.addAll(images);
        pageLiveData.postValue(DataState.createSuccessState(images));
    }

    private List<Image> mapPostsToImages(List<ImgurPost> posts) {
        List<Image> images = new ArrayList<>();
        for (ImgurPost post : posts) {
            if (post.getImagesCount() > 0) {
                mapAndReduceImages(images, post, post.getPostImages());
            }
        }
        return images;
    }

    private void mapAndReduceImages(List<Image> reducer, ImgurPost post,
                                    List<PostImage> postImages) {
        for (PostImage image : postImages) {
            if (image.getType().contains(IMAGE_PREFIX)) {
                Image mappedImage = new Image(image.getId(), image.getLink(), post.getAuthor(),
                        post.getTitle());
                reducer.add(mappedImage);
            }
        }
    }

    private void onLoadError(Throwable error) {
        --page;
        pageLiveData.postValue(DataState.createErrorState(error));
        postProperError(error);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        postsRepository.cancelPendingRequests();
    }

}