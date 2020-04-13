package com.srise.theme;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ChangeableActivity extends AppCompatActivity implements SkinUtil.ISkinChange {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinUtil.getInstance().registerSkin(this);
        init();
    }

    protected abstract void init();

    @Override
    public void change() {
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinUtil.getInstance().unregisterSkin(this);
    }
}
