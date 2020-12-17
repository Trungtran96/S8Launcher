package com.launcher.samsung.galaxy.galaxys8launcher.models;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Trung Tran Thanh on 10/4/2017.
 */

public class S8TextView extends TextView {

    public S8TextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public S8TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public S8TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("font_s8.ttf", context);
        setTypeface(customFont);
    }
}
