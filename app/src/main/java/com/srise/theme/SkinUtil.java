package com.srise.theme;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.PathClassLoader;

public class SkinUtil {
    private static SkinUtil sInstance;
    private static final String TAG = "SkinUtil";
    private Context mContext;
    private List<ISkinChange> mChangeSkinList;
    private Map<String, Object> mSkinAttrMap;
    private boolean mDefaultChangeToken = false;
    private boolean mDynamicChangeToken = false;


    public boolean isDefaultChangeToken() {
        return mDefaultChangeToken;
    }

    public void setDefaultChangeToken(boolean defaultChangeToken) {
        this.mDefaultChangeToken = defaultChangeToken;
    }

    public boolean isDynamicChangeToken() {
        return mDynamicChangeToken;
    }

    public void setDynamicChangeToken(boolean dynamicChangeToken) {
        this.mDynamicChangeToken = dynamicChangeToken;
    }

    private SkinUtil() {
        mChangeSkinList = new ArrayList<>();
        mSkinAttrMap = new HashMap<>();
    }

    public static final SkinUtil getInstance() {
        if (sInstance == null) {
            sInstance = new SkinUtil();
        }

        return sInstance;
    }

    public void init(Context context) {
        mContext = context;
        loadDefault();
    }

    private void loadDefault() {
        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("drawable.png");
            Drawable drawable = BitmapDrawable.createFromStream(inputStream, null);
            mSkinAttrMap.put("text_color", mContext.getResources().getColor(R.color.txt_color, null));
            mSkinAttrMap.put("drawable", drawable);
            mSkinAttrMap.put("inner_drawable", mContext.getResources().getDrawable(R.drawable.inner_drawable, null));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

    public Object getSkinAttr(String attrKey) {
        return mSkinAttrMap.get(attrKey);
    }

    public void loadSkin(String path) {
        try {
            Log.d(TAG, path);

            if ("default".equals(path)) {
                loadDefault();
            } else {
                InputStream inputStream = null;

                try {
                    File apkFile = new File(path);
                    PackageManager packageManager = mContext.getPackageManager();
                    PackageInfo PackageInfo = packageManager.getPackageArchiveInfo(apkFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
                    Log.d(TAG, PackageInfo.packageName);

                    AssetManager assetManager = AssetManager.class.newInstance();
                    Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                    addAssetPath.invoke(assetManager, apkFile.getAbsolutePath());
                    Resources res = mContext.getResources();
                    Resources otherRes = new Resources(assetManager, res.getDisplayMetrics(), res.getConfiguration());

                    PathClassLoader dexClassLoader = new PathClassLoader(apkFile.getAbsolutePath(), mContext.getClassLoader());

                    Class<?> clazz = dexClassLoader.loadClass(PackageInfo.packageName + ".R$string");
                    Field field = clazz.getDeclaredField("app_name");
                    int id = field.getInt(clazz);
                    otherRes.getString(id);

                    clazz = dexClassLoader.loadClass(PackageInfo.packageName + ".R$color");
                    field = clazz.getDeclaredField("text_color");
                    id = field.getInt(clazz);
                    mSkinAttrMap.put("text_color", otherRes.getColor(id, null));

                    clazz = dexClassLoader.loadClass(PackageInfo.packageName + ".R$drawable");
                    field = clazz.getDeclaredField("inner_drawable");
                    id = field.getInt(clazz);
                    mSkinAttrMap.put("inner_drawable", otherRes.getDrawable(id, null));

                    assetManager = otherRes.getAssets();
                    inputStream = assetManager.open("drawable.png");
                    Drawable drawable = BitmapDrawable.createFromStream(inputStream, null);
                    mSkinAttrMap.put("drawable", drawable);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }
            }

            for (ISkinChange change : mChangeSkinList) {
                change.change();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void registerSkin(ISkinChange skinChange) {
        mChangeSkinList.add(skinChange);
    }

    public void unregisterSkin(ISkinChange skinChange) {
        mChangeSkinList.remove(skinChange);
    }

    public interface ISkinChange {
        void change();
    }
}
