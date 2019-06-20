package com.graisvictory.imgur.ui.common;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private List<T> data;

    public void setItems(List<T> items) {
        DiffCallback callback = new DiffCallback<T>(data, items, this::checkSameItems,
                this::checkSameContent);
        int previousSize = data != null ? data.size() : 0;
        data = items != null ? new ArrayList<>(items) : null;

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        diffResult.dispatchUpdatesTo(this);

        if (previousSize != 0) {
            notifyItemRangeRemoved(0, previousSize);
        }

        int newSize = data != null ? data.size() : 0;
        if (newSize != 0) {
            notifyItemRangeInserted(0, newSize);
        }
    }

    public void addItems(List<T> items) {
        if (data == null) {
            data = new ArrayList<>();
        }

        data.addAll(items);

        int lastPosition = data.size() - 1;
        int addedItemsSize = items.size();
        notifyItemRangeInserted(lastPosition, lastPosition + addedItemsSize);
    }

    protected T getItem(int position) {
        return data.get(position);
    }

    public void clear() {
        setItems(null);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    protected abstract boolean checkSameItems(T oldItem, T newItem);

    protected abstract boolean checkSameContent(T oldItem, T newItem);

    private static class DiffCallback<T> extends DiffUtil.Callback {

        private List<T> oldList;
        private List<T> newList;
        private BiFunction<T, Boolean> itemsComparator;
        private BiFunction<T, Boolean> contentComparator;

        DiffCallback(List<T> oldList, List<T> newList,
                            BiFunction<T, Boolean> itemsComparator,
                            BiFunction<T, Boolean> contentComparator) {
            this.oldList = oldList;
            this.newList = newList;
            this.itemsComparator = itemsComparator;
            this.contentComparator = contentComparator;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldList == null || newList == null) {
                return false;
            }
            T oldItem = oldList.get(oldItemPosition);
            T newItem = newList.get(newItemPosition);
            return itemsComparator.apply(oldItem, newItem);
        }

        @Override
        public int getOldListSize() {
            return oldList != null ? oldList.size() : 0;
        }

        @Override
        public int getNewListSize() {
            return newList != null ? newList.size() : 0;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            if (oldList == null || newList == null) {
                return false;
            }
            T oldItem = oldList.get(oldItemPosition);
            T newItem = newList.get(newItemPosition);
            return contentComparator.apply(oldItem, newItem);
        }
    }

    @FunctionalInterface
    private interface BiFunction<T, R> {

        @NonNull
        R apply(T first, T second);

    }

}