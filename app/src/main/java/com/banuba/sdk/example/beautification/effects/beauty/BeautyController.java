package com.banuba.sdk.example.beautification.effects.beauty;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.banuba.sdk.example.beautification.effects.EffectValuesView;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersData;

public class BeautyController implements VSListSelectorListener, EffectValuesView  {
    // UI components model
    private VSModel mModel;

    // Parent view for selected effect ui components
    private ViewGroup mValuesParentView;

    // Listener which calls js methods
    private ModelDataListener mModelDataListener;

    private List<ValueSetter> mActiveGroup;
    private VSGroupSelector mGroupSelector;
    private int mActiveGroupIndex = 0;

    public BeautyController(
        RecyclerView VSGroupSelectorView,
        ViewGroup valuesView,
        ModelDataListener modelDataListener,
        HashMap<String, ArrayList<Parcelable>> settersData) {
        mModel = new VSModel(modelDataListener, settersData);

        mGroupSelector = new VSGroupSelector(this, VSGroupSelectorView);
        mValuesParentView = valuesView;
        mModelDataListener = modelDataListener;
    }

    /**
     * Used to reinitialize current effect group UI setters on selected group changed event
     * @param name New selected group name
     */
    @Override
    public void onVSListSelectorValueChanged(String name, int index) {
        mActiveGroupIndex = index;
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
    public void activate(int activeGroupIndex) {
        mActiveGroupIndex = activeGroupIndex;
        mGroupSelector.populate(mModel.getGroupNames(), activeGroupIndex);
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

    @Override
    public int getActiveGroupIndex() {
        return mActiveGroupIndex;
    }

    public HashMap<String, ArrayList<SettersData>>  getSettersData() {
        return mModel.getSettersData();
    }
}
