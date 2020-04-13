package com.srise.theme.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatImageView;

import com.srise.theme.R;
import com.srise.theme.SkinUtil;

public class MyImageView extends AppCompatImageView {
    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myAttr);
        String assetDrawableStr = typedArray.getString(R.styleable.myAttr_asset_drawable);
        String innerDrawableStr = typedArray.getString(R.styleable.myAttr_inner_drawable);
        typedArray.recycle();

        Drawable drawable = null;

        if (assetDrawableStr != null) {
            drawable = (Drawable) SkinUtil.getInstance().getSkinAttr(assetDrawableStr);
        } else {
            drawable = (Drawable) SkinUtil.getInstance().getSkinAttr(innerDrawableStr);
        }

        setImageDrawable(drawable);
    }
}
