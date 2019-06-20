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
import com.graisvictory.imgur.ui.common.BaseAdapter;
import com.graisvictory.imgur.viewmodel.images.Image;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesAdapter extends BaseAdapter<Image, ImagesAdapter.ImageHolder> {

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_image, parent, false);
        return new ImageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Image postImage = getItem(position);
        holder.bind(postImage);
    }

    @Override
    protected boolean checkSameItems(Image oldItem, Image newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    protected boolean checkSameContent(Image oldItem, Image newItem) {
        return oldItem.getId().equals(newItem.getId())
                && oldItem.getAuthor().equals(newItem.getAuthor())
                && oldItem.getLink().equals(newItem.getLink())
                && oldItem.getTitle().equals(newItem.getTitle());
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
