package com.srise.theme;

import android.app.Application;

import com.srise.theme.SkinUtil;

public class MyAppication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinUtil.getInstance().init(this);
    }
}
