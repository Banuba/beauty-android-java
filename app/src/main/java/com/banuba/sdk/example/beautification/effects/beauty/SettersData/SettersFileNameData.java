package com.banuba.sdk.example.beautification.effects.beauty.SettersData;

import android.os.Parcel;

public class SettersFileNameData extends SettersData {
    public String mFileName;

    public SettersFileNameData(String fileName) {
        mFileName = fileName;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mFileName);
    }
}
