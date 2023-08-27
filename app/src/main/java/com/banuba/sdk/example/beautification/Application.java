package com.banuba.sdk.example.beautification;

import com.banuba.sdk.example.common.BanubaClientToken;
import com.banuba.sdk.manager.BanubaSdkManager;

import java.util.Objects;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BanubaSdkManager.initialize(Objects.requireNonNull(getApplicationContext()), BanubaClientToken.KEY);
    }

    @Override
    public void onTerminate() {
        BanubaSdkManager.deinitialize();
        super.onTerminate();
    }
}
