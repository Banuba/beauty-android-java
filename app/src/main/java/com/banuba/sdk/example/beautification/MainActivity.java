package com.banuba.sdk.example.beautification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.banuba.sdk.example.beautification.adapters.StringListAdapter;
import com.banuba.sdk.example.beautification.data.DataRepository;
import com.banuba.sdk.example.beautification.makeup.MakeupApi;
import com.banuba.sdk.input.CameraDevice;
import com.banuba.sdk.input.CameraInput;
import com.banuba.sdk.output.SurfaceOutput;
import com.banuba.sdk.player.Player;
import com.rtugeek.android.colorseekbar.ColorSeekBar;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 1234;

    private CameraDevice cameraDevice; // The player receives frames from this camera
    private SurfaceOutput surfaceOutput; // The player renders the image to here
    private final Player player = new Player(); // The banuba sdk player
    private final MakeupApi makeupApi = new MakeupApi(js -> player.evalJs(js, null)); // Makeup Effect API with callback

    private RecyclerView groupsListView;
    private RecyclerView sectionsListView;
    private ColorSeekBar colorSelector;
    private SeekBar valueSetter;
    private Button actionButton;
    private LinearLayout fiveColorsGroup;
    private TextView[] fiveColors;

    private MainActivityViewModel viewModel;

    private int indexOfFiveColors = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initPlayer();
        observeData();

        if (isCameraPermissionGranted()) {
            cameraDevice.start();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    private boolean isCameraPermissionGranted() {
        return Build.VERSION.SDK_INT < 23
                || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                cameraDevice.start();
            } else {
                Toast.makeText(this, "No camera permissions", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.play();
    }

    @Override
    protected void onDestroy() {
        player.close();
        surfaceOutput.close();
        super.onDestroy();
    }

    private void initViews() {
        groupsListView = findViewById(R.id.groupView);
        groupsListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        sectionsListView = findViewById(R.id.sectionView);
        sectionsListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        colorSelector = findViewById(R.id.colorSelector);

        valueSetter = findViewById(R.id.valueSetter);

        actionButton = findViewById(R.id.actionButton);

        fiveColorsGroup = findViewById(R.id.fiveColorsGroup);
        fiveColors = new TextView[] {
            findViewById(R.id.color1),
            findViewById(R.id.color2),
            findViewById(R.id.color3),
            findViewById(R.id.color4),
            findViewById(R.id.color5)
        };
    }

    private void initPlayer() {
        // initialize the input
        cameraDevice = new CameraDevice(this, MainActivity.this);
        final CameraInput cameraInput = new CameraInput(cameraDevice);

        // initialize the output
        final SurfaceView effectView = findViewById(R.id.effectView);
        surfaceOutput = new SurfaceOutput(effectView.getHolder());

        // use input from the front camera and output to the surface
        player.use(cameraInput, surfaceOutput);
        player.loadAsync("effects/Makeup");
    }

    private void observeData() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        viewModel.getGroupListLiveData().observe(MainActivity.this, this::prepareUiForGroups);

        viewModel.getSectionListLiveData().observe(MainActivity.this, this::prepareUiForSections);

        viewModel.getSectionLiveData().observe(MainActivity.this, section -> {
            prepareUiForColoring(section);
            prepareUiForBeautyOrMorphing(section);
            prepareUiForClearing(section);
        });
    }

    private void prepareUiForGroups(List<DataRepository.Group> groupList) {
        if (groupList != null) {
            final List<String> strings = DataRepository.makeStringListFromGroupList(groupList);
            final StringListAdapter adapter = new StringListAdapter(strings, viewModel::setCurrentGroupIndex);
            adapter.setSelectedItemIndex(viewModel.getCurrentGroupIndex());

            groupsListView.setAdapter(adapter);
            groupsListView.scrollToPosition(viewModel.getCurrentGroupIndex());
            groupsListView.setVisibility(View.VISIBLE);
        } else {
            groupsListView.setVisibility(View.GONE);
        }
    }

    private void prepareUiForSections(List<DataRepository.Section> sectionList) {
        if (sectionList != null) {
            if (sectionList.size() > 1) {
                final List<String> strings = DataRepository.makeStringListFromSectionList(sectionList);
                final StringListAdapter adapter = new StringListAdapter(strings, viewModel::setCurrentSectionIndex);
                adapter.setSelectedItemIndex(viewModel.getCurrentSectionIndex());

                sectionsListView.setAdapter(adapter);
                sectionsListView.scrollToPosition(viewModel.getCurrentSectionIndex());

                sectionsListView.setVisibility(View.VISIBLE);
            } else {
                viewModel.setCurrentSectionIndex(0);
                sectionsListView.setVisibility(View.GONE);
            }
        } else {
            sectionsListView.setVisibility(View.GONE);
        }
    }

    private void prepareUiForColoring(DataRepository.Section section) {
        if (section != null && section.coloring != null) {
            indexOfFiveColors = 0;

            final MakeupApi.ColoringApi coloring = section.coloring;
            final boolean upToFiveColors = coloring.upToFiveColors();

            colorSelector.setOnColorChangeListener((colorBarPosition, alphaBarPosition, colorWithAlpha) -> {
                if (upToFiveColors) {
                    // apply changes to five colors ui and call js method
                    applyColorToFiveColorsUi(colorWithAlpha, indexOfFiveColors);
                    final int hashOfCurrentColor = Objects.hash(coloring, fiveColors[indexOfFiveColors]);
                    viewModel.setValue(hashOfCurrentColor, colorWithAlpha);

                    final int[] colors = new int[] {0, 0, 0, 0, 0};
                    int length = 0;
                    for (int i = 0; i < 5; i++) {
                        final int hash = Objects.hash(coloring, fiveColors[i]);
                        final int color = viewModel.getValue(hash);
                        if (Color.alpha(color) != 0) {
                            colors[length++] = color;
                        }
                    }

                    // call coloring with a gradient of up to five colors
                    makeupApi.getJsBuilder().color(coloring, colors, length == 0 ? 1 : length).call();
                } else {
                    viewModel.setValue(coloring.hashCode(), colorWithAlpha);

                    // call coloring with only one color
                    makeupApi.getJsBuilder().color(coloring, colorWithAlpha).call();
                }
            });

            if (upToFiveColors) {
                // initialize ui with five colors
                for (int i = 0; i < 5; i++) {
                    final TextView view = fiveColors[i];
                    final int hash = Objects.hash(coloring, view);

                    final int index = i;
                    view.setOnClickListener(v -> {
                        final int color = viewModel.getValue(hash);
                        indexOfFiveColors = index;
                        applyColorToFiveColorsUi(color, index);
                        colorSelector.setAlphaBarPosition(0xff - Color.alpha(color));
                        colorSelector.setColor(color);
                    });

                    final int initialColor = viewModel.getValue(hash);
                    applyColorToFiveColorsUi(initialColor, i);
                    if (i == indexOfFiveColors) {
                        colorSelector.setAlphaBarPosition(0xff - Color.alpha(initialColor));
                        colorSelector.setColor(initialColor);
                    }
                }

                fiveColorsGroup.setVisibility(View.VISIBLE);
            } else {
                final int color = viewModel.getValue(coloring.hashCode());
                colorSelector.setAlphaBarPosition(0xff - Color.alpha(color));
                colorSelector.setColor(color);

                fiveColorsGroup.setVisibility(View.GONE);
            }

            colorSelector.setVisibility(View.VISIBLE);
        } else {
            colorSelector.setOnColorChangeListener(null);
            colorSelector.setVisibility(View.GONE);
            fiveColorsGroup.setVisibility(View.GONE);
        }
    }

    private void prepareUiForBeautyOrMorphing(DataRepository.Section section) {
        if (section != null && (section.beauty != null || section.morphing != null)) {
            final MakeupApi.BeautyApi beauty = section.beauty;
            final MakeupApi.MorphApi morph = section.morphing;

            final int hash = beauty != null ? beauty.hashCode() : morph.hashCode();
            final float min = morph != null ? morph.min() : 0.0f;
            final float max = morph != null ? morph.max() : 1.0f;

            valueSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    viewModel.setValue(hash, progress);
                    float value = toRange(0.0f, valueSetter.getMax(), progress, min, max);
                    // call any beauty or morph method
                    if (beauty != null) {
                        makeupApi.getJsBuilder().beauty(beauty, value).call();
                    } else {
                        makeupApi.getJsBuilder().morph(morph, value).call();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            final int defaultValue = (int) toRange(min, max, 0.0f, 0.0f, valueSetter.getMax());
            valueSetter.setProgress(viewModel.getValue(hash, defaultValue));

            valueSetter.setVisibility(View.VISIBLE);
        } else {
            valueSetter.setOnSeekBarChangeListener(null);
            valueSetter.setVisibility(View.GONE);
        }
    }

    private void prepareUiForClearing(DataRepository.Section section) {
        if (section != null && section.clearing != null) {
            actionButton.setOnClickListener(view -> {
                // call clear method
                makeupApi.getJsBuilder().clear(section.clearing).call();
                viewModel.clearValues();
            });
            actionButton.setText(section.text);
            actionButton.setVisibility(View.VISIBLE);
        } else {
            actionButton.setVisibility(View.GONE);
        }
    }

    private void applyColorToFiveColorsUi(int color, int indexOfCurentColorUi) {
        final TextView view = fiveColors[indexOfCurentColorUi];
        final GradientDrawable drawable = (GradientDrawable) view.getBackground();
        int invColor = Color.argb(255, 255 - Color.red(color), 255 - Color.green(color), 255 - Color.blue(color));
        drawable.setColor(color);
        view.setTextColor(invColor);

        for (int i = 0; i < 5; i++) {
            final GradientDrawable borderDrawable = (GradientDrawable) fiveColors[i].getBackground();
            borderDrawable.setStroke(5, i == indexOfFiveColors ? 0xFFFF9800 : 0xFFFFFFFF);
        }
    }

    private float toRange(float inMin, float inMax, float inVal, float outMin, float outMax) {
        return (outMax - outMin) * (inVal - inMin) / (inMax - inMin) + outMin;
    }
}
