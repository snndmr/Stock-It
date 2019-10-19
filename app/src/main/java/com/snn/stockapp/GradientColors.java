package com.snn.stockapp;

import android.graphics.drawable.GradientDrawable;

class GradientColors {
    static final GradientDrawable TEXT_VIEW_BACKGROUND
            = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
            new int[]{
                    android.graphics.Color.parseColor("#000000"),
                    android.graphics.Color.parseColor("#E6000000"),
                    android.graphics.Color.parseColor("#CC000000"),
                    android.graphics.Color.parseColor("#B3000000"),
                    android.graphics.Color.parseColor("#99000000"),
                    android.graphics.Color.parseColor("#80000000"),
                    android.graphics.Color.parseColor("#66000000"),
                    android.graphics.Color.parseColor("#4D000000"),
                    android.graphics.Color.parseColor("#33000000"),
                    android.graphics.Color.parseColor("#1A000000"),
                    android.graphics.Color.parseColor("#00000000")
            });

    static final GradientDrawable BACKGROUND
            = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
            new int[]{
                    android.graphics.Color.parseColor("#3b8d99"),
                    android.graphics.Color.parseColor("#6b6b83"),
                    android.graphics.Color.parseColor("#aa4b6b"),
            });
}
