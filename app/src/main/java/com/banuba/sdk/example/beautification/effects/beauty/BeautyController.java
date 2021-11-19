package com.banuba.sdk.example.beautification.effects.beauty;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import com.banuba.sdk.example.beautification.effects.EffectValuesView;

public class BeautyController implements VSListSelectorListener, EffectValuesView  {
    // UI components model
    private VSModel mModel;

    // Parent view for selected effect ui components
    private ViewGroup mValuesParentView;

    // Listener which calls js methods
    private ModelDataListener mModelDataListener;

    private List<ValueSetter> mActiveGroup;
    private VSGroupSelector mGroupSelector;

    public BeautyController(
        RecyclerView VSGroupSelectorView,
        ViewGroup valuesView,
        ModelDataListener modelDataListener) {
        mModel = new VSModel(modelDataListener);

        mGroupSelector = new VSGroupSelector(this, VSGroupSelectorView);
        mValuesParentView = valuesView;
        mModelDataListener = modelDataListener;
    }

    /**
     * Used to reinitialize current effect group UI setters on selected group changed event
     * @param name New selected group name
     */
    @Override
    public void onVSListSelectorValueChanged(String name) {
        mValuesParentView.removeAllViews();
        resetActiveSetters(mModel.getGroup(name));

        LayoutInflater inflater = LayoutInflater.from(mValuesParentView.getContext());
        for (ValueSetter item : mActiveGroup) {
            item.addToViewGroup(inflater, mValuesParentView);
        }
    }

    private void resetActiveSetters(List<ValueSetter> newSetters) {
        if (mActiveGroup != null) {
            for (ValueSetter item : mActiveGroup) {
                item.setView(null);
            }
        }
        mActiveGroup = newSetters;
    }

    /**
     * Show beautification UI and call effect update
     */
    @Override
    public void activate() {
        mGroupSelector.populate(mModel.getGroupNames());
    }

    /**
     * Hide beautification UI
     */
    @Override
    public void deactivate() {
        resetActiveSetters(null);
        mGroupSelector.clear();
        mValuesParentView.removeAllViews();
    }
}
