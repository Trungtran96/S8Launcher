package com.launcher.samsung.galaxy.galaxys8launcher.models;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;


/**
 * Created by Trung Tran Thanh on 10/4/2017.
 */

public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontName, Context context) {
        Typeface typeface = fontCache.get(fontName);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontName, typeface);
        }

        return typeface;
    }
}
