package com.banuba.sdk.example.beautification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banuba.sdk.example.beautification.effects.EffectController;
import com.banuba.sdk.example.beautification.effects.beauty.ModelDataListener;
import com.banuba.sdk.example.beautification.effects.color.ColorValueListener;
import com.banuba.sdk.effect_player.Effect;
import com.banuba.sdk.manager.BanubaSdkManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
    implements ModelDataListener, AdapterView.OnItemSelectedListener, ColorValueListener {
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 10072;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 112;

    private static final Map<String, String> mEffects = new HashMap<String, String>() {
        {
            put("Beauty", "Beauty_base");
            put("Eyes", "test_Eyes");
            put("Hair", "test_Hair");
            put("Lips", "test_Lips");
            put("Skin", "test_Skin");
        }
    };

    private EffectController mEffectController = null;
    private BanubaSdkManager mSdkManager;
    private Effect mCurrentEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestCameraPermission();
        requestWriteStoragePermission();

        setContentView(R.layout.activity_main);

        List<String> sortedEffectNames = new ArrayList<>(mEffects.keySet());
        Collections.sort(sortedEffectNames);

        ArrayAdapter<String> effectSpinnerAdapter =
            new ArrayAdapter<>(this, R.layout.effect_spinner_dropdown_item, sortedEffectNames);

        final Spinner effect_spinner = findViewById(R.id.effect_selector);
        effect_spinner.setAdapter(effectSpinnerAdapter);
        effect_spinner.setOnItemSelectedListener(this);

        final Spinner presets_selector = findViewById(R.id.beauty_presets_selector);

        final SurfaceView sv = findViewById(R.id.effect_view);

        BanubaSdkManager.initialize(this, BanubaClientToken.KEY);
        mSdkManager = new BanubaSdkManager(this);
        mSdkManager.attachSurface(sv);

        final RecyclerView effectItemView = findViewById(R.id.effect_selector_view);
        final ViewGroup effectValuesView = findViewById(R.id.effect_parameters_view);
        mEffectController =
            new EffectController(effectItemView, effectValuesView, presets_selector, this, this);
    }

    private boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            return checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private void requestCameraPermission() {
        if (!isCameraPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this, new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSION);
        }
    }

    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private void requestWriteStoragePermission() {
        boolean permissionGranted = isStoragePermissionGranted();
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(
                this,
                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(
        int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSION:
                final boolean resultCamera = isCameraPermissionGranted();
                if (resultCamera) {
                    mSdkManager.openCamera();
                } else {
                    Toast.makeText(this, "No camera permissions", Toast.LENGTH_LONG).show();
                }
            case REQUEST_WRITE_STORAGE_PERMISSION:
                final boolean resultStorage = isStoragePermissionGranted();
                if (!resultStorage) {
                    Toast.makeText(this, "No storage permissions", Toast.LENGTH_LONG).show();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onModelDataChanged(Map<String, String> values) {
        String json = new JSONObject(values).toString();
        Log.d("beauty json", json);
        mCurrentEffect.callJsMethod("onDataUpdate", json);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Objects.requireNonNull(mSdkManager.getEffectPlayer()).playbackPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSdkManager.openCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Objects.requireNonNull(mSdkManager.getEffectPlayer()).playbackPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSdkManager.closeCamera();
    }

    @Override
    protected void onDestroy() {
        mCurrentEffect = null;
        mSdkManager.releaseSurface();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = (String) ((TextView) view).getText();
        loadEffect(mEffects.get(selected));
    }

    private void loadEffect(String effect) {
        mCurrentEffect = mSdkManager.loadEffect(BanubaSdkManager.getResourcesBase() + "/effects/" + effect, false);
        mEffectController.onEffectChanged(effect);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onColorChanged(int argb) {
        Log.d("color json", "ARGB color: " + String.format("%08X", argb));

        // clang-format off
        int a = (argb >> 24) & (0x000000FF);
        int r = (argb >> 16) & (0x000000FF);
        int g = (argb >> 8) & (0x000000FF);
        int b = argb & (0x000000FF);
        // clang-format on

        List<Float> color_arr = new ArrayList<>(4);
        color_arr.add((float) r / 0xFF);
        color_arr.add((float) g / 0xFF);
        color_arr.add((float) b / 0xFF);
        color_arr.add((float) a / 0xFF);

        String json = new JSONArray(color_arr).toString();
        Log.d("color json", "json: " + json);
        mCurrentEffect.callJsMethod("setColor", json);
    }
}