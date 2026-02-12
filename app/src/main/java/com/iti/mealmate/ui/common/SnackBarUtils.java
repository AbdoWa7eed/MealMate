package com.iti.mealmate.ui.common;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorRes;

import com.google.android.material.snackbar.Snackbar;
import com.iti.mealmate.R;

public class SnackBarUtils {

    public static void showHomeSnackBar(Activity activity, String msg, @ColorRes int backgroundColorRes) {
        showSnackBar(activity, msg, backgroundColorRes, R.dimen.snackbar_margin_horizontal, R.dimen.snackbar_margin_bottom_home);
    }

    public static void showHomeErrorSnackBar(Activity activity, String msg) {
        showHomeSnackBar(activity, msg, R.color.colorError);
    }

    public static void showHomeSuccessSnackBar(Activity activity, String msg) {
        showHomeSnackBar(activity, msg, R.color.colorSuccess);
    }

    public static void showAuthSnackBar(Activity activity, String msg, @ColorRes int backgroundColorRes) {
        showSnackBar(activity, msg, backgroundColorRes, R.dimen.snackbar_margin_horizontal, R.dimen.snackbar_margin_bottom_auth);
    }

    public static void showAuthErrorSnackBar(Activity activity, String msg) {
        showAuthSnackBar(activity, msg, R.color.colorError);
    }

    private static void showSnackBar(Activity activity, String msg, @ColorRes int backgroundColorRes, int marginHorizontalRes, int marginBottomRes) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(activity.getColor(backgroundColorRes));
        snackbar.setTextColor(activity.getColor(R.color.colorOnPrimary));

        View snackbarView = snackbar.getView();
        int marginBottom = activity.getResources().getDimensionPixelSize(marginBottomRes);
        int marginHorizontal = activity.getResources().getDimensionPixelSize(marginHorizontalRes);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snackbarView.getLayoutParams();
        params.setMargins(marginHorizontal, 0, marginHorizontal, marginBottom);
        snackbarView.setLayoutParams(params);

        snackbar.show();
    }


}
