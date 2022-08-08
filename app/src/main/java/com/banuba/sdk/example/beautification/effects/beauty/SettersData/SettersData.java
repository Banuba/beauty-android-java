package com.banuba.sdk.example.beautification.effects.beauty.SettersData;

import android.os.Parcel;
import android.os.Parcelable;

public class SettersData implements Parcelable {

    protected SettersData(Parcel in) {
    }

    public SettersData() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SettersData> CREATOR = new Creator<SettersData>() {
        @Override
        public SettersData createFromParcel(Parcel in) {
            return new SettersData(in);
        }

        @Override
        public SettersData[] newArray(int size) {
            return new SettersData[size];
        }
    };
}
