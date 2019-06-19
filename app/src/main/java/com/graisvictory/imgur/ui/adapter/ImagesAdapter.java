package com.graisvictory.imgur.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.graisvictory.imgur.R;
import com.graisvictory.imgur.viewmodel.images.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageHolder> {

    private List<Image> data;

    public void setItems(List<Image> items) {
        int previousSize = data != null ? data.size() : 0;
        data = items != null ? new ArrayList<>(items) : null;

        if (previousSize != 0) {
            notifyItemRangeRemoved(0, previousSize);
        }

        int newSize = data != null ? data.size() : 0;
        if (newSize != 0) {
            notifyItemRangeInserted(0, newSize);
        }
    }

    public void addItems(List<Image> items) {
        if (data == null) {
            data = new ArrayList<>();
        }

        data.addAll(items);

        int lastPosition = data.size() - 1;
        int addedItemsSize = items.size();
        notifyItemRangeInserted(lastPosition, lastPosition + addedItemsSize);
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_image, parent, false);
        return new ImageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Image postImage = data.get(position);
        holder.bind(postImage);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.imageTitle)
        TextView title;
        @BindView(R.id.imageAuthor)
        TextView author;

        ImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Image postImage) {
            title.setText(postImage.getTitle());
            author.setText(postImage.getAuthor());
            Glide.with(itemView)
                    .load(postImage.getLink())
                    .placeholder(R.drawable.ic_loading)
                    .into(imageView);
        }
    }

}
