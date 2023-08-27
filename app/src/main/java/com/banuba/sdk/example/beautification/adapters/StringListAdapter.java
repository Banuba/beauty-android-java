package com.banuba.sdk.example.beautification.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.banuba.sdk.example.beautification.R;

import java.util.List;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.ItemViewHolder> {
    public interface IItemClickListener {
        void onClick(int index);
    }

    private final static int DEFAULT_COLOR = 0xFFFFFFFF;
    private final static int SELECTED_COLOR = 0xFFFF9800;

    private final List<String> mDisplayList;
    private final IItemClickListener mItemClickCallback;

    private int mSelectedPosition = -1;

    public StringListAdapter(@NonNull List<String> displayList, @NonNull IItemClickListener callback) {
        mDisplayList = displayList;
        mItemClickCallback = callback;
    }

    public int getSelectedItemIndex() {
        return mSelectedPosition;
    }

    public void setSelectedItemIndex(int index) {
        if (mSelectedPosition != -1) {
            notifyItemChanged(mSelectedPosition);
        }
        mSelectedPosition = index;
        if (index != -1) {
            notifyItemChanged(index);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.spinner_item, null);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final String itemText = mDisplayList.get(position);
        holder.textView.setText(itemText);
        holder.textView.setTextColor(position == getSelectedItemIndex() ? SELECTED_COLOR : DEFAULT_COLOR);
        holder.itemView.setOnClickListener(v -> {
            setSelectedItemIndex(getSelectedItemIndex() == holder.getAdapterPosition() ? -1 : holder.getAdapterPosition());
            mItemClickCallback.onClick(getSelectedItemIndex());
        });
    }

    @Override
    public int getItemCount() {
        return mDisplayList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
