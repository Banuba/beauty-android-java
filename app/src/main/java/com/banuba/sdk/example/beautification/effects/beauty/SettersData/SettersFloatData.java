package com.banuba.sdk.example.beautification.effects.beauty.SettersData;

import android.os.Parcel;

public class SettersFloatData extends SettersData {
    public int mValue;

    public SettersFloatData(int value) {
        mValue = value;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mValue);
    }
}
