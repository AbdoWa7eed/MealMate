package com.iti.mealmate.ui.common;

import android.content.Context;

public class UiUtils {

    private UiUtils() {}
    public static int  dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }
}
