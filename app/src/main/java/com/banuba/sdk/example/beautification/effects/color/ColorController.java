package com.banuba.sdk.example.beautification.effects.color;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.banuba.sdk.example.beautification.R;
import com.banuba.sdk.example.beautification.effects.EffectValuesView;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

public class ColorController implements EffectValuesView, ColorSeekBar.OnColorChangeListener {
    private ViewGroup mValuesParentView;
    private ColorValueListener mColorValueListener;

    private int mCurrentColor = 0;
    private int mAlphaBarPosition = 0;

    public ColorController(ViewGroup valuesView, ColorValueListener colorValueListener) {
        mValuesParentView = valuesView;
        mColorValueListener = colorValueListener;
    }

    @Override
    public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
        if (mCurrentColor == color) {
            return;
        }

        mCurrentColor = color;
        mAlphaBarPosition = alphaBarPosition;

        emitValuesUpdate();
    }

    @Override
    public void activate() {
        LayoutInflater inflater = LayoutInflater.from(mValuesParentView.getContext());
        ColorSeekBar colorPicker =
            (ColorSeekBar) inflater.inflate(R.layout.color_selector, mValuesParentView, false);
        colorPicker.setOnColorChangeListener(this);
        colorPicker.setColor(mCurrentColor);
        colorPicker.setAlphaBarPosition(mAlphaBarPosition);
        mValuesParentView.addView(colorPicker);
        emitValuesUpdate();
    }

    @Override
    public void deactivate() {
        mValuesParentView.removeAllViews();
    }

    private void emitValuesUpdate() {
        mColorValueListener.onColorChanged(mCurrentColor);
    }
}
