package com.srise.theme.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.srise.theme.R;
import com.srise.theme.SkinUtil;

public class MyTextView extends AppCompatTextView {
    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.myAttr);
        String textColor = typedArray.getString(R.styleable.myAttr_t_color);
        typedArray.recycle();

        setTextColor((int) SkinUtil.getInstance().getSkinAttr(textColor));
    }
}
