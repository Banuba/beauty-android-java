package com.banuba.sdk.example.beautification.effects;

import com.banuba.sdk.example.beautification.effects.beauty.SettersData.SettersData;

import java.util.ArrayList;
import java.util.HashMap;

public interface EffectValuesView {
    void activate(int activeGroupIndex);
    void deactivate();
    int getActiveGroupIndex();
    HashMap<String, ArrayList<SettersData>> getSettersData();
}
