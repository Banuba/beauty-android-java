package com.banuba.sdk.example.beautification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banuba.sdk.example.beautification.effects.EffectController;
import com.banuba.sdk.example.beautification.effects.beauty.ModelDataListener;
import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersData;
import com.banuba.sdk.input.CameraDevice;
import com.banuba.sdk.input.CameraInput;
import com.banuba.sdk.manager.BanubaSdkManager;
import com.banuba.sdk.output.SurfaceOutput;
import com.banuba.sdk.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class MyColor {
    public float r;
    public float g;
    public float b;
    public float a;
    public boolean used;

    public MyColor() {
        set(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        used = a > 0.01f;
    }
}

public class MainActivity extends AppCompatActivity implements ModelDataListener {
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 10072;
    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 112;
    private static final String QUOTE = "\"";

    private EffectController mEffectController = null;

    private Player mPlayer;
    private SurfaceOutput mSurfaceOutput;
    private CameraDevice mCameraDevice;

    private HashMap<String, MyColor[]> mHairColorMap;
    static final String ACTIVE_GROUP_INDEX = "activeGroupIndex";
    static final int DEFAULT_ACTIVE_GROUP_INDEX = 0;
    static final String GROUPS_NAMES_LIST = "groupsNamesList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int activeGroupIndex = DEFAULT_ACTIVE_GROUP_INDEX;
        ArrayList<String> groupsNames;
        HashMap<String, ArrayList<Parcelable>> settersData = null;
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            activeGroupIndex = savedInstanceState.getInt(ACTIVE_GROUP_INDEX);
            groupsNames = savedInstanceState.getStringArrayList(GROUPS_NAMES_LIST);
            settersData = new HashMap<>();
            for(String name : groupsNames) {
                ArrayList<Parcelable> currentSettersData =
                        savedInstanceState.getParcelableArrayList(name);
                settersData.put(name, currentSettersData);
            }
        }

        requestWriteStoragePermission();

        setContentView(R.layout.activity_main);

        BanubaSdkManager.initialize(this, BanubaClientToken.KEY);

        final SurfaceView surfaceView = findViewById(R.id.effect_view);
        mPlayer = new Player();
        mSurfaceOutput = new SurfaceOutput(surfaceView.getHolder());
        mPlayer.use(mSurfaceOutput);
        mPlayer.loadAsync("effects/Makeup");

        final RecyclerView effectItemView = findViewById(R.id.effect_selector_view);
        final ViewGroup effectValuesView = findViewById(R.id.effect_parameters_view);

        mEffectController = new EffectController(effectItemView, effectValuesView, this, settersData);
        mEffectController.onEffectChanged("Makeup", activeGroupIndex);

        if (isCameraPermissionGranted()) {
            startCameraPreview();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA_PERMISSION);
        }
    }

    private boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            return checkSelfPermission(Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
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
                    startCameraPreview();
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

    public void startCameraPreview() {
        mCameraDevice = new CameraDevice(this, MainActivity.this);
        mCameraDevice.start();
        mPlayer.use(new CameraInput(mCameraDevice));
    }

    @Override
    public void onSetterLoadImage(String key, String path) {
        mPlayer.evalJs(makeJsMethod(key, "'" + path + "'"), null);
    }

    @Override
    public void onSetterEvent(String key) {
        mPlayer.evalJs(makeJsMethod(key, null), null);
    }

    @Override
    public void onSetterFloatValueChanged(String key, float value) {
        mPlayer.evalJs(makeJsMethod(key, Float.toString(value)), null);
    }

    @Override
    public void onSetterRgbaValueChanged(String key, float r, float g, float b, float a) {
        String arg = rgbaToString(r, g, b, a);
        mPlayer.evalJs(makeJsMethod(key, arg), null);
    }

    @Override
    public void onSetterRgbaMultipleColorsValueChanged(String key, float r, float g, float b, float a, int index) {
        if (index < 0 || index > 4) {
            throw new IllegalArgumentException("Invalid index: " + index);
        }

        if (mHairColorMap == null) {
            mHairColorMap = new HashMap<String, MyColor[]>();
        }

        if (!mHairColorMap.containsKey(key)) {
            mHairColorMap.put(key, new MyColor[] {
                new MyColor(), new MyColor(), new MyColor(), new MyColor(), new MyColor()
            });
        }

        MyColor[] colors = mHairColorMap.get(key);
        colors[index].set(r, g, b, a);

        ArrayList<String> args = new ArrayList<String>();
        for (MyColor cl: colors) {
            if (cl.used) {
                String arg = rgbaToString(cl.r, cl.g, cl.b, cl.a);
                args.add(arg);
            }
        }

        String arg = rgbaToString(0.0f, 0.0f, 0.0f, 0.0f);
        if (!args.isEmpty()) {
            arg = args.toString();
        }
        mPlayer.evalJs(makeJsMethod(key, arg), null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer.play();
    }

    @Override
    protected void onDestroy() {
        if (mCameraDevice != null) {
            mCameraDevice.close();
        }
        mPlayer.close();
        mSurfaceOutput.close();
        super.onDestroy();
    }

    private String rgbaToString(float r, float g, float b, float a) {
        return String.format(Locale.US, "'%f %f %f %f'", r, g, b, a);
    }

    private String makeJsMethod(String method, String params) {
        return method + (params == null ? "()" : "(" + params + ")");
    }

    private int getActiveGroupIndex() {
        return mEffectController.getActiveGroupIndex();
    }

    private HashMap<String, ArrayList<SettersData>>  getSettersData() {
        return mEffectController.getSettersData();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(ACTIVE_GROUP_INDEX, getActiveGroupIndex());
        ArrayList<String> groupsNames = new ArrayList<>();
        HashMap<String, ArrayList<SettersData>>  settersData = getSettersData();
        for(Map.Entry<String, ArrayList<SettersData>> entry : settersData.entrySet()) {
            String name = entry.getKey();
            ArrayList<SettersData> currentSettersData = entry.getValue();
            savedInstanceState.putParcelableArrayList(name, currentSettersData);
            groupsNames.add(name);
        }
        savedInstanceState.putStringArrayList(GROUPS_NAMES_LIST, groupsNames);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
