package com.snn.stockapp;

import android.graphics.drawable.GradientDrawable;

class GradientColors {
    static final GradientDrawable BACKGROUND
            = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
            new int[]{
                    android.graphics.Color.parseColor("#b92b27"),
                    android.graphics.Color.parseColor("#1565C0")
            });
}
