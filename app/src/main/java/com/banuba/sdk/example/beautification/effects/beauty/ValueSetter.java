package com.banuba.sdk.example.beautification.effects.beauty;

import android.os.Environment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Button;
import android.view.*;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.banuba.sdk.example.beautification.R;
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
}

class LoadImageButtonValueSetter extends ValueSetter implements View.OnClickListener, DialogSelectionListener {
    protected Button mView;

    LoadImageButtonValueSetter(String displayName, String parameterName, ModelDataListener cb) {
        super(displayName, parameterName, cb);
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
            mValueListener.onSetterLoadImage(getParameterName(), files[0]);
        }
    }

}

class SeekBarFloatValueSetter extends ValueSetter implements SeekBar.OnSeekBarChangeListener {
    final static protected int SCALE = 1000;

    protected SeekBar mView;
    protected int myValue = 0;

    SeekBarFloatValueSetter(String displayName, String parameterName, ModelDataListener cb) {
        super(displayName, parameterName, cb);
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
    protected int mColor = 0;
    protected int mAlpha = 255;
    protected int mIndex = -1;

    SeekBarRgbaValueSetter(String displayName, String parameterName, ModelDataListener cb) {
        super(displayName, parameterName, cb);
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
            mView.setColorBarPosition(mColor);
            mView.setAlphaBarPosition(mAlpha);
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
    public void onColorChangeListener(int colorBarPosition, int alphaBarPosition, int color) {
        mColor = colorBarPosition;
        mAlpha = alphaBarPosition;

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

class SwitchValueSetter extends ValueSetter implements Switch.OnCheckedChangeListener {
    private Switch mView;
    private boolean myValue = false;
    private String paramNameOff;

    SwitchValueSetter(String displayName, String parameterNameOn, String parameterNameOff, ModelDataListener cb) {
        super(displayName, parameterNameOn, cb);
        paramNameOff = parameterNameOff;
    }

    @Override
    void setView(View view) {
        if (view != null && !(view instanceof Switch)) {
            throw new IllegalArgumentException("Invalid view type: " + view.toString());
        }
        mView = (Switch) view;
        if (mView != null) {
            mView.setOnCheckedChangeListener(this);
            mView.setChecked(myValue);
        }
    }

    @Override
    void addToViewGroup(LayoutInflater inflater, ViewGroup valuesParentView) {
        TextView tv = (TextView) inflater.inflate(R.layout.vsetter_title, valuesParentView, false);
        tv.setText(getDisplayName());
        valuesParentView.addView(tv);

        View view = inflater.inflate(R.layout.vsetter_switch, valuesParentView, false);
        View setter = view.findViewById(R.id.value_setter);
        setView(setter);
        valuesParentView.addView(view);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        myValue = b;
        if (b) {
            mValueListener.onSetterEvent(getParameterName());
        } else {
            mValueListener.onSetterEvent(paramNameOff);
        }
    }
}