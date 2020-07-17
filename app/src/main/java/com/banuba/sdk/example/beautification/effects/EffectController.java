package com.banuba.sdk.example.beautification.effects;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.banuba.sdk.example.beautification.effects.beauty.BeautyController;
import com.banuba.sdk.example.beautification.effects.beauty.ModelDataListener;
import com.banuba.sdk.example.beautification.effects.color.ColorController;
import com.banuba.sdk.example.beautification.effects.color.ColorValueListener;

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
        Spinner presetsSelector,
        ModelDataListener modelDataListener,
        ColorValueListener colorValueListener) {
        BeautyController beauty =
            new BeautyController(selectorView, valuesView, presetsSelector, modelDataListener);
        ColorController color = new ColorController(valuesView, colorValueListener);

        mEffectViews = new HashMap<String, EffectValuesView>() {
            {
                put("Beauty_base", beauty);
                put("test_Eyes", color);
                put("test_Hair", color);
                put("test_Lips", color);
                put("test_Skin", color);
            }
        };
    }

    public void onEffectChanged(String effect) {
        if (current_effect.equals(effect)) {
            return;
        }

        EffectValuesView prev = mEffectViews.get(current_effect);
        if (prev != null) {
            prev.deactivate();
        }

        EffectValuesView current = mEffectViews.get(effect);
        if (current != null) {
            current.activate();
        }

        current_effect = effect;
    }
}
