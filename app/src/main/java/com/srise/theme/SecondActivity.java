package com.srise.theme;

import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class SecondActivity extends ChangeableActivity {
    private static final String TAG = "SecondActivity";

    protected void init() {
        setContentView(R.layout.activity_main2);
        Button btn = findViewById(R.id.btn);
        Button btn2 = findViewById(R.id.btn2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SkinUtil.getInstance().isDefaultChangeToken()) {
                    setTheme(R.style.DarkAppTheme);
                } else {
                    setTheme(R.style.LightAppTheme);
                }

                SkinUtil.getInstance().setDefaultChangeToken(!SkinUtil.getInstance().isDefaultChangeToken());
                init();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinUtil.getInstance().loadSkin();
            }
        });
    }
}
