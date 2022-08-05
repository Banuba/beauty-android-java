package com.banuba.sdk.example.beautification.effects;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.ViewGroup;

import com.banuba.sdk.example.beautification.effects.beauty.BeautyController;
import com.banuba.sdk.example.beautification.effects.beauty.ModelDataListener;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EffectController {
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private Map<String, EffectValuesView> mEffectViews;

    private String current_effect = "";

    /**
     * @param selectorView      Parent view for effect selector items
     * @param valuesView        Parent view for effect value setters
     * @param modelDataListener Listener for effect values change events
     */
    public EffectController(
        RecyclerView selectorView,
        ViewGroup valuesView,
        ModelDataListener modelDataListener,
        HashMap<String, ArrayList<Parcelable>> settersData) {

        BeautyController makeup = new BeautyController(selectorView, valuesView, modelDataListener, settersData);
        mEffectViews = new HashMap<String, EffectValuesView>() {
            {
                put("Makeup", makeup);
            }
        };
    }

    public void onEffectChanged(String effect, int activeGroupIndex) {
        if (current_effect.equals(effect)) {
            return;
        }

        EffectValuesView prev = mEffectViews.get(current_effect);
        if (prev != null) {
            prev.deactivate();
        }

        EffectValuesView current = mEffectViews.get(effect);
        if (current != null) {
            current.activate(activeGroupIndex);
        }

        current_effect = effect;
    }

    public int getActiveGroupIndex() {
        EffectValuesView currentView = mEffectViews.get(current_effect);
        return currentView.getActiveGroupIndex();
    }

    public HashMap<String, ArrayList<SettersData>>  getSettersData() {
        EffectValuesView currentView = mEffectViews.get(current_effect);
        return currentView.getSettersData();
    }
}
