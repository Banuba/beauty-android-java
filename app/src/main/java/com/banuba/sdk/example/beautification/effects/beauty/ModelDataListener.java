package com.banuba.sdk.example.beautification.effects.beauty;

public interface ModelDataListener {
    void onSetterLoadImage(String key, String path);
    void onSetterRgbaMultipleColorsValueChanged(String key, float r, float g, float b, float a, int index);
    void onSetterEvent(String key);
    void onSetterFloatValueChanged(String key, float value);
    void onSetterRgbaValueChanged(String key, float r, float g, float b, float a);
}
