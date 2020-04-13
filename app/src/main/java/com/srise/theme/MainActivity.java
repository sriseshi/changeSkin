package com.srise.theme;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends ChangeableActivity {
    private static final String TAG = "app:MainActivity";

    protected void init() {
        setContentView(R.layout.activity_main);
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
                if (SkinUtil.getInstance().isDynamicChangeToken()) {
                    SkinUtil.getInstance().loadSkin("default");
                } else {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "b.apk";
                    SkinUtil.getInstance().loadSkin(path);
                }

                SkinUtil.getInstance().setDynamicChangeToken(!SkinUtil.getInstance().isDynamicChangeToken());
            }
        });

        Button next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }
}
