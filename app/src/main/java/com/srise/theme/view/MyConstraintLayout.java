package com.srise.theme.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.srise.theme.R;
import com.srise.theme.SkinUtil;

public class MyConstraintLayout extends ConstraintLayout {
    public static final String ATTR_BACKGROUND_COLOR = "background_color";

    public MyConstraintLayout(Context context) {
        this(context, null);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myAttr);
        String bgColoStr = typedArray.getString(R.styleable.myAttr_b_color);
        typedArray.recycle();
        setBackgroundColor((int) SkinUtil.getInstance().getSkinAttr(bgColoStr));
    }
}
