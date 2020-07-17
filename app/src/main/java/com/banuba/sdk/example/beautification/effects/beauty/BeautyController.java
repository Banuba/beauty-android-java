package com.banuba.sdk.example.beautification.effects.beauty;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.banuba.sdk.example.beautification.R;
import com.banuba.sdk.example.beautification.effects.EffectValuesView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BeautyController implements ValueSetterListener, VSListSelectorListener,
                                         EffectValuesView, AdapterView.OnItemSelectedListener {
    private final static String CUSTOM_OPTION = "Custom";

    private final Map<String, String> mModelData = new HashMap<String, String>() {
        {
            put(ModelDataKeys.MORPH_CHEEKS, "0.0");
            put(ModelDataKeys.MORPH_EYES, "0.0");
            put(ModelDataKeys.MORPH_NOSE, "0.0");

            put(ModelDataKeys.SKIN_SOFT, "0.0");
            put(ModelDataKeys.SOFTLIGHT_ALPHA, "0.0");
            put(ModelDataKeys.SOFTLIGHT_TEX, "0");

            put(ModelDataKeys.EYES_FLARE_ALPHA, "0.0");
            put(ModelDataKeys.EYES_COLORING, "0.0");

            put(ModelDataKeys.TEETH_WHITENING, "0.0");

            put(ModelDataKeys.LASHES_TEX, "0");
            put(ModelDataKeys.LASHES_ALPHA, "0.0");

            put(ModelDataKeys.EYEBROWS_TEX, "0");
            put(ModelDataKeys.EYEBROWS_ALPHA, "0.0");

            put(ModelDataKeys.FINAL_COLOR_CORRECTION_TEX, "0");
            put(ModelDataKeys.FINAL_COLOR_CORRECTION, "0.0");
        }
    };

    private final Map<String, Map<String, String>> mPresets =
        new LinkedHashMap<String, Map<String, String>>() {
            {
                put(CUSTOM_OPTION, null);
                put("Disabled", new HashMap<String, String>() {
                    {
                        put(ModelDataKeys.MORPH_CHEEKS, "0.0");
                        put(ModelDataKeys.MORPH_EYES, "0.0");
                        put(ModelDataKeys.MORPH_NOSE, "0.0");

                        put(ModelDataKeys.SKIN_SOFT, "0.0");
                        put(ModelDataKeys.SOFTLIGHT_ALPHA, "0.0");
                        put(ModelDataKeys.SOFTLIGHT_TEX, "0");

                        put(ModelDataKeys.EYES_FLARE_ALPHA, "0.0");
                        put(ModelDataKeys.EYES_COLORING, "0.0");

                        put(ModelDataKeys.TEETH_WHITENING, "0.0");

                        put(ModelDataKeys.LASHES_TEX, "0");
                        put(ModelDataKeys.LASHES_ALPHA, "0.0");

                        put(ModelDataKeys.EYEBROWS_TEX, "0");
                        put(ModelDataKeys.EYEBROWS_ALPHA, "0.0");

                        put(ModelDataKeys.FINAL_COLOR_CORRECTION_TEX, "0");
                        put(ModelDataKeys.FINAL_COLOR_CORRECTION, "0.0");
                    }
                });
                put("Lesser", new HashMap<String, String>() {
                    {
                        put(ModelDataKeys.MORPH_CHEEKS, "0.5");
                        put(ModelDataKeys.MORPH_EYES, "0.5");
                        put(ModelDataKeys.MORPH_NOSE, "0.5");

                        put(ModelDataKeys.SKIN_SOFT, "0.5");
                        put(ModelDataKeys.SOFTLIGHT_ALPHA, "0.5");
                        put(ModelDataKeys.SOFTLIGHT_TEX, "0");

                        put(ModelDataKeys.EYES_FLARE_ALPHA, "0.4");
                        put(ModelDataKeys.EYES_COLORING, "0.4");

                        put(ModelDataKeys.TEETH_WHITENING, "0.4");

                        put(ModelDataKeys.LASHES_TEX, "0");
                        put(ModelDataKeys.LASHES_ALPHA, "0.5");

                        put(ModelDataKeys.EYEBROWS_TEX, "0");
                        put(ModelDataKeys.EYEBROWS_ALPHA, "0.4");

                        put(ModelDataKeys.FINAL_COLOR_CORRECTION_TEX, "0");
                        put(ModelDataKeys.FINAL_COLOR_CORRECTION, "0.4");
                    }
                });
                put("Medium", new HashMap<String, String>() {
                    {
                        put(ModelDataKeys.MORPH_CHEEKS, "1.0");
                        put(ModelDataKeys.MORPH_EYES, "1.0");
                        put(ModelDataKeys.MORPH_NOSE, "1.0");

                        put(ModelDataKeys.SKIN_SOFT, "1.0");
                        put(ModelDataKeys.SOFTLIGHT_ALPHA, "0.7");
                        put(ModelDataKeys.SOFTLIGHT_TEX, "0");

                        put(ModelDataKeys.EYES_FLARE_ALPHA, "0.7");
                        put(ModelDataKeys.EYES_COLORING, "0.7");

                        put(ModelDataKeys.TEETH_WHITENING, "0.7");

                        put(ModelDataKeys.LASHES_TEX, "0");
                        put(ModelDataKeys.LASHES_ALPHA, "1.0");

                        put(ModelDataKeys.EYEBROWS_TEX, "0");
                        put(ModelDataKeys.EYEBROWS_ALPHA, "1.0");

                        put(ModelDataKeys.FINAL_COLOR_CORRECTION_TEX, "0");
                        put(ModelDataKeys.FINAL_COLOR_CORRECTION, "0.7");
                    }
                });
                put("Extreme", new HashMap<String, String>() {
                    {
                        put(ModelDataKeys.MORPH_CHEEKS, "2.0");
                        put(ModelDataKeys.MORPH_EYES, "2.0");
                        put(ModelDataKeys.MORPH_NOSE, "2.0");

                        put(ModelDataKeys.SKIN_SOFT, "2.0");
                        put(ModelDataKeys.SOFTLIGHT_ALPHA, "1.0");
                        put(ModelDataKeys.SOFTLIGHT_TEX, "0");

                        put(ModelDataKeys.EYES_FLARE_ALPHA, "1.0");
                        put(ModelDataKeys.EYES_COLORING, "1.0");

                        put(ModelDataKeys.TEETH_WHITENING, "1.0");

                        put(ModelDataKeys.LASHES_TEX, "0");
                        put(ModelDataKeys.LASHES_ALPHA, "2.0");

                        put(ModelDataKeys.EYEBROWS_TEX, "1");
                        put(ModelDataKeys.EYEBROWS_ALPHA, "2.0");

                        put(ModelDataKeys.FINAL_COLOR_CORRECTION_TEX, "0");
                        put(ModelDataKeys.FINAL_COLOR_CORRECTION, "1.0");
                    }
                });
            }
        };

    // UI components model
    private final VSModel mModel = new VSModel(this);

    // Parent view for selected effect ui components
    private ViewGroup mValuesParentView;

    // Listener which calls js methods
    private ModelDataListener mModelDataListener;

    private List<ValueSetter> mActiveGroup;
    private VSGroupSelector mGroupSelector;
    private Spinner mPresetsSelector;

    public BeautyController(
        RecyclerView VSGroupSelectorView,
        ViewGroup valuesView,
        Spinner presetsSelector,
        ModelDataListener modelDataListener) {
        mGroupSelector = new VSGroupSelector(this, VSGroupSelectorView);
        mValuesParentView = valuesView;
        mModelDataListener = modelDataListener;
        mPresetsSelector = presetsSelector;

        ArrayAdapter<String> presets_selector_adapter = new ArrayAdapter<>(
            presetsSelector.getContext(),
            R.layout.effect_spinner_dropdown_item,
            new ArrayList<>(mPresets.keySet()));
        presetsSelector.setAdapter(presets_selector_adapter);
        presetsSelector.setOnItemSelectedListener(this);
    }

    /**
     * Used to emit model data changed event on any effect value change
     * @param name  Changed effect parameter name
     * @param value Changed effect value
     */
    @Override
    public void onSetterValueChanged(String name, String value) {
        String v = mModelData.get(name);
        if (v == null) {
            throw new RuntimeException("Wrong value assign attempt: " + name);
        }
        if (v.equals(value)) {
            return;
        }
        mModelData.put(name, value);
        emitValuesUpdate();
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
            TextView tv =
                (TextView) inflater.inflate(R.layout.vsetter_title, mValuesParentView, false);
            tv.setText(item.getDisplayName());
            mValuesParentView.addView(tv);

            View view;
            if (item instanceof SeekBarValueSetter) {
                view = inflater.inflate(R.layout.vsetter_seekbar, mValuesParentView, false);
            } else if (item instanceof SpinnerValueSetter) {
                view =
                    inflater.inflate(R.layout.vsetter_resource_selector, mValuesParentView, false);
            } else if (item instanceof SwitchValueSetter) {
                view = inflater.inflate(R.layout.vsetter_switch, mValuesParentView, false);
            } else {
                throw new RuntimeException("Unexpected type: " + item.getClass().getName());
            }

            View setter = view.findViewById(R.id.value_setter);
            String value = mModelData.get(item.getParameterName());
            if (value == null) {
                throw new RuntimeException("No value for key: " + item.getParameterName());
            }

            item.setView(setter);
            item.setValue(value);

            mValuesParentView.addView(view);
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
        mPresetsSelector.setVisibility(View.VISIBLE);
        mPresetsSelector.setSelection(0);
        mGroupSelector.populate(mModel.getGroupNames());
        emitValuesUpdate();
    }

    /**
     * Hide beautification UI
     */
    @Override
    public void deactivate() {
        mPresetsSelector.setVisibility(View.GONE);
        resetActiveSetters(null);
        mGroupSelector.clear();
        mValuesParentView.removeAllViews();
    }

    /**
     * Send data to effect
     */
    private void emitValuesUpdate() {
        mModelDataListener.onModelDataChanged(mModelData);
    }

    /**
     * On preset selected
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String preset = (String) ((TextView) view).getText();

        if (preset.equals(CUSTOM_OPTION)) {
            mGroupSelector.populate(mModel.getGroupNames());
            return;
        }

        resetActiveSetters(null);
        mGroupSelector.clear();
        mValuesParentView.removeAllViews();

        mModelData.putAll(Objects.requireNonNull(mPresets.get(preset)));
        emitValuesUpdate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
