package com.banuba.sdk.example.beautification.effects.beauty;

import android.os.Environment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Button;
import android.view.*;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.banuba.sdk.example.beautification.R;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersData;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersFileNameData;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersFloatData;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersRGBAData;
import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

import java.io.File;

// View model base class for setter elements
abstract class ValueSetter {
    private String mDisplayName;
    protected String mParameterName;
    protected ModelDataListener mValueListener;

    ValueSetter(String displayName, String parameterName, ModelDataListener cb) {
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

    abstract void setView(View view);

    abstract void addToViewGroup(LayoutInflater inflater, ViewGroup valuesParentView);

    abstract SettersData getSettersData();
}

class LoadImageButtonValueSetter extends ValueSetter implements View.OnClickListener, DialogSelectionListener {
    protected Button mView;
    private String mFileName = "";

    LoadImageButtonValueSetter(String displayName, String parameterName, ModelDataListener cb, SettersFileNameData data) {
        super(displayName, parameterName, cb);
        if(data != null) {
            mFileName = data.mFileName;
//            onSelectedFilePaths(new String[]{mFileName});
        }
    }

    @Override
    void setView(View view) {
        if (view != null && !(view instanceof Button)) {
            throw new IllegalArgumentException("Invalid view type: " + view.toString());
        }
        mView = (Button) view;
        if (mView != null) {
            mView.setText(getDisplayName());
            mView.setOnClickListener(this);
        }
    }

    @Override
    void addToViewGroup(LayoutInflater inflater, ViewGroup valuesParentView) {
        View view = inflater.inflate(R.layout.vsetter_image_load, valuesParentView, false);
        View setter = view.findViewById(R.id.value_setter);
        setView(setter);
        valuesParentView.addView(view);
    }

    @Override
    SettersData getSettersData() {
        return new SettersFileNameData(mFileName);
    }

    @Override
    public void onClick(View v) {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = Environment.getExternalStorageDirectory();
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = new String[] {"jpeg", "jpg", "png", "ktx", "gif"};
        properties.show_hidden_files = false;

        FilePickerDialog dialog = new FilePickerDialog((AppCompatActivity)mValueListener, properties);
        dialog.setDialogSelectionListener(this);
        dialog.setTitle("Select an image file");
        dialog.show();
    }

    @Override
    public void onSelectedFilePaths(String[] files) {
        //files is the array of the paths of files selected by the Application User.
        if (files.length == 1) {
            mFileName = files[0];
            mValueListener.onSetterLoadImage(getParameterName(), files[0]);
        }
    }
}

class SeekBarFloatValueSetter extends ValueSetter implements SeekBar.OnSeekBarChangeListener {
    final static protected int SCALE = 1000;

    protected SeekBar mView;
    protected int myValue = 0;

    SeekBarFloatValueSetter(String displayName, String parameterName, ModelDataListener cb, SettersFloatData data) {
        super(displayName, parameterName, cb);
        if(data != null) {
            myValue = data.mValue;
            mValueListener.onSetterFloatValueChanged(getParameterName(), (float)myValue / SCALE);
        }
    }

    @Override
    void setView(View view) {
        if (view != null && !(view instanceof SeekBar)) {
            throw new IllegalArgumentException("Invalid view type: " + view.toString());
        }
        mView = (SeekBar) view;
        if (mView != null) {
            mView.setMax(SCALE);
            mView.setOnSeekBarChangeListener(this);
            mView.setProgress(myValue);
        }
    }

    @Override
    void addToViewGroup(LayoutInflater inflater, ViewGroup valuesParentView) {
        TextView tv = (TextView) inflater.inflate(R.layout.vsetter_title, valuesParentView, false);
        tv.setText(getDisplayName());
        valuesParentView.addView(tv);

        View view = inflater.inflate(R.layout.vsetter_seekbar, valuesParentView, false);
        View setter = view.findViewById(R.id.value_setter);
        setView(setter);
        valuesParentView.addView(view);
    }

    @Override
    SettersData getSettersData() {
        return new SettersFloatData(myValue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        myValue = i;
        mValueListener.onSetterFloatValueChanged(getParameterName(), (float)myValue / SCALE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}

class SeekBarRgbaValueSetter extends ValueSetter implements ColorSeekBar.OnColorChangeListener {
    final static protected int SCALE = 1000;

    protected ColorSeekBar mView;
    protected int mColorBarPosition = 0;
    protected int mColor = 0;
    protected int mAlphaBarPosition = 255;
    protected int mIndex = -1;

    SeekBarRgbaValueSetter(String displayName, String parameterName, ModelDataListener cb, SettersRGBAData data) {
        super(displayName, parameterName, cb);
        if(data != null) {
            mColorBarPosition = data.mColorBarPosition;
            mColor = data.mColor;
            mAlphaBarPosition = data.mAlphaBarPosition;
            mIndex = data.mIndex;
            onColorChangeListener(mColorBarPosition, mAlphaBarPosition, mColor);
        }
    }

    SeekBarRgbaValueSetter(String displayName, String parameterName, ModelDataListener cb, int index) {
        super(displayName, parameterName, cb);
        mIndex = index;
    }

    @Override
    void setView(View view) {
        if (view != null && !(view instanceof ColorSeekBar)) {
            throw new IllegalArgumentException("Invalid view type: " + view.toString());
        }
        mView = (ColorSeekBar) view;
        if (mView != null) {
            mView.setMaxPosition(SCALE);
            mView.setColorBarPosition(mColorBarPosition);
            mView.setAlphaBarPosition(mAlphaBarPosition);
            mView.setOnColorChangeListener(this);
        }
    }

    @Override
    void addToViewGroup(LayoutInflater inflater, ViewGroup valuesParentView) {
        if (getDisplayName() != null) {
            TextView tv = (TextView) inflater.inflate(R.layout.vsetter_title, valuesParentView, false);
            tv.setText(getDisplayName());
            valuesParentView.addView(tv);
        }

        View view = inflater.inflate(R.layout.color_selector, valuesParentView, false);
        View setter = view.findViewById(R.id.color_selector);
        setView(setter);
        valuesParentView.addView(view);
    }

    @Override
    SettersRGBAData getSettersData() {
        return new SettersRGBAData(mColorBarPosition, mColor, mAlphaBarPosition, mIndex);
    }

    @Override
    public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
        mColorBarPosition = colorBarPosition;
        mAlphaBarPosition = alphaBarPosition;
        mColor = color;

        float alpha = (float)((color >> 24) & 0xff) / 0xff;
        float red = (float)((color >> 16) & 0xff) / 0xff;
        float green = (float)((color >> 8) & 0xff) / 0xff;
        float blue= (float)(color & 0xff) / 0xff;

        if (mIndex == -1) {
            mValueListener.onSetterRgbaValueChanged(getParameterName(), red, green, blue, alpha);
        } else {
            mValueListener.onSetterRgbaMultipleColorsValueChanged(getParameterName(), red, green, blue, alpha, mIndex);
        }
    }
}
