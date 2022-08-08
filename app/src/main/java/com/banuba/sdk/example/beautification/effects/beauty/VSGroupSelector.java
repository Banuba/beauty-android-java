package com.banuba.sdk.example.beautification.effects.beauty;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.banuba.sdk.example.beautification.R;

import java.util.List;

/**
 * Controller for group selector
 */
final class VSGroupSelector {
    // Listener for changed group event
    private VSListSelectorListener mListener;
    // Parent view for group items
    private RecyclerView mView;

    VSGroupSelector(VSListSelectorListener listener, RecyclerView view) {
        mListener = listener;
        mView = view;
    }

    /**
     * Populate group selector with values
     * @param effects List of elements for selector
     */
    void populate(List<String> effects, int activeGroupIndex) {
        mView.setVisibility(View.VISIBLE);
        mView.setLayoutManager(
            new LinearLayoutManager(mView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mView.setAdapter(new EffectSelectorViewAdapter(effects, activeGroupIndex, mListener, mView));
    }

    void clear() {
        mView.setVisibility(View.GONE);
        mView.setAdapter(null);
        mView.removeAllViews();
    }
}

class EffectSelectorViewAdapter extends RecyclerView.Adapter<EffectSelectorViewAdapter.ViewHolder>
    implements View.OnClickListener {
    private List<String> mEffects;
    private RecyclerView mView;
    private VSListSelectorListener mListener;

    private int mSelectedIdx;

    EffectSelectorViewAdapter(
        List<String> effects, int activeGroupIndex, VSListSelectorListener listener, RecyclerView view) {
        mEffects = effects;
        mListener = listener;
        mView = view;
        mSelectedIdx = activeGroupIndex;
        setSelectedGroup(activeGroupIndex);
    }

    @Override
    public void onClick(View view) {
        int idx = mView.getChildLayoutPosition(view);
        if (mSelectedIdx == idx) {
            return;
        }
        setSelectedGroup(idx);
    }

    private void setSelectedGroup(int idx) {
        mSelectedIdx = idx;
        mListener.onVSListSelectorValueChanged(mEffects.get(mSelectedIdx), mSelectedIdx);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.group_spinner_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mEffects.get(i), i == mSelectedIdx);
    }

    @Override
    public int getItemCount() {
        return mEffects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private int mSelectedColor;
        private int mDefaultColor;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.effect_selector_item_text);
            mSelectedColor = ContextCompat.getColor(itemView.getContext(), R.color.colorAccent);
            mDefaultColor = ContextCompat.getColor(itemView.getContext(), R.color.textColor);
        }

        void bind(String item, boolean selected) {
            nameTextView.setText(item);
            nameTextView.setTextColor(selected ? mSelectedColor : mDefaultColor);
        }
    }
}