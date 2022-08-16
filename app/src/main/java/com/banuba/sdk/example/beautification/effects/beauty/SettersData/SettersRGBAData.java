package com.banuba.sdk.example.beautification.effects.beauty.SettersData;

import android.os.Parcel;

public class SettersRGBAData extends SettersData {
    public int mColorBarPosition;
    public int mColor;
    public int mAlphaBarPosition;
    public int mIndex;

    public SettersRGBAData(int colorBarPosition, int color, int alphaBarPosition, int index) {
        mColorBarPosition = colorBarPosition;
        mColor = color;
        mAlphaBarPosition = alphaBarPosition;
        mIndex = index;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mColorBarPosition);
        parcel.writeInt(mColor);
        parcel.writeInt(mAlphaBarPosition);
        parcel.writeInt(mIndex);
    }
}
