package com.banuba.sdk.example.beautification.effects.beauty;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.List;
import java.util.Map;

// View model base class for setter elements
abstract class ValueSetter implements ModelDataListener {
    private String mDisplayName;
    private String mParameterName;
    private ValueSetterListener mValueListener;

    ValueSetter(String displayName, String parameterName, ValueSetterListener cb) {
        mDisplayName = displayName;
        mParameterName = parameterName;
        mValueListener = cb;
    }

    final String getDisplayName() {
        return mDisplayName;
    }
    final String getParameterName() {
        return mParameterName;
    }

    abstract void setValue(String value);
    protected abstract String getValue();

    abstract void setView(View view);

    final void emitValueChanged() {
        mValueListener.onSetterValueChanged(mParameterName, getValue());
    }

    @Override
    final public void onModelDataChanged(Map<String, String> values) {
        setValue(values.get(mParameterName));
    }
}

class SeekBarValueSetter extends ValueSetter implements SeekBar.OnSeekBarChangeListener {
    final static private int SCALE = 100;

    private int mMax = 1;
    private SeekBar mView;

    SeekBarValueSetter(String displayName, String parameterName, ValueSetterListener cb) {
        super(displayName, parameterName, cb);
    }

    SeekBarValueSetter(String displayName, String parameterName, ValueSetterListener cb, int max) {
        this(displayName, parameterName, cb);
        mMax = max;
    }

    @Override
    void setValue(String value) {
        if (mView != null) {
            mView.setProgress((int) (Float.parseFloat(value) * SCALE));
        }
    }

    @Override
    protected String getValue() {
        return Float.toString((float) (mView.getProgress()) / SCALE);
    }

    @Override
    void setView(View view) {
        if (view != null && !(view instanceof SeekBar)) {
            throw new IllegalArgumentException("Invalid view type: " + view.toString());
        }
        mView = (SeekBar) view;
        if (mView != null) {
            mView.setMax(mMax * SCALE);
            mView.setOnSeekBarChangeListener(this);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        emitValueChanged();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}

class SpinnerValueSetter extends ValueSetter implements Spinner.OnItemSelectedListener {
    private List<String> mAvailableValues;
    private Spinner mView;

    SpinnerValueSetter(
        String displayName, String parameterName, List<String> values, ValueSetterListener cb) {
        super(displayName, parameterName, cb);
        mAvailableValues = values;
    }

    private List<String> getAvailableValues() {
        return mAvailableValues;
    }

    @Override
    void setValue(String value) {
        if (mView != null) {
            int v = Integer.parseInt(value);
            if (v < 0 || v >= mAvailableValues.size()) {
                throw new IndexOutOfBoundsException("Value out of range: " + value);
            }
            mView.setSelection(Integer.parseInt(value), false);
        }
    }

    @Override
    protected String getValue() {
        return Integer.toString(mView.getSelectedItemPosition());
    }

    @Override
    void setView(View view) {
        if (view != null && !(view instanceof Spinner)) {
            throw new IllegalArgumentException("Invalid view type: " + view.toString());
        }
        mView = (Spinner) view;
        if (mView != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                mView.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getAvailableValues());

            mView.setOnItemSelectedListener(this);
            mView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        emitValueChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}

class SwitchValueSetter extends ValueSetter implements Switch.OnCheckedChangeListener {
    private Switch mView;
    private final static String ENABLED_VALUE = "1";
    private final static String DISABLED_VALUE = "0";

    SwitchValueSetter(String displayName, String parameterName, ValueSetterListener cb) {
        super(displayName, parameterName, cb);
    }

    @Override
    void setValue(String value) {
        if (!value.equals(ENABLED_VALUE) && !value.equals(DISABLED_VALUE)) {
            throw new IllegalArgumentException("Invalid setValue argument: " + value);
        }
        mView.setChecked(value.equals(ENABLED_VALUE));
    }

    @Override
    protected String getValue() {
        return mView.isChecked() ? ENABLED_VALUE : DISABLED_VALUE;
    }

    @Override
    void setView(View view) {
        if (view != null && !(view instanceof Switch)) {
            throw new IllegalArgumentException("Invalid view type: " + view.toString());
        }
        mView = (Switch) view;
        if (mView != null) {
            mView.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        emitValueChanged();
    }
}