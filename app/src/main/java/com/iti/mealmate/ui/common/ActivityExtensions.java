package com.iti.mealmate.ui.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.snackbar.Snackbar;
import com.iti.mealmate.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ActivityExtensions {
    public static void setStatusBarColor(Activity activity, int colorRes, boolean lightIcons) {
        int color = ContextCompat.getColor(activity, colorRes);
        if (Build.VERSION.SDK_INT < 35) {
            activity.getWindow().setStatusBarColor(color);
        } else {
            activity.getWindow().getDecorView().setBackgroundColor(color);
        }
        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(activity.getWindow(), activity.getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(lightIcons);
    }

    public static void setStatusBarWithDarkIcons(Activity activity) {
        ActivityExtensions.setStatusBarColor(activity, R.color.colorBackground, true);
    }

    public static void setStatusBarColor(Activity activity) {
        setStatusBarColor(activity, R.color.colorBackground, false);
    }

    public static void setStatusBarTransparent(Activity activity) {
        activity.getWindow().setStatusBarColor(activity.getColor(android.R.color.transparent));
        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(activity.getWindow(), activity.getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true); // Dark icons for light imagery, or false for dark imagery.
        // Assuming details screen hero image is dark-ish or user wants transparency over it. 
        // MealDetailsActivity sets dark icons.
    }

    public static void navigateToActivity(Activity activity, Class<?> target, Bundle extras) {
        Intent intent = new Intent(activity, target);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivity(intent);
    }



    public static void navigateAndFinish(Activity activity, Class<?> target) {
        navigateToActivity(activity, target, null);
        activity.finish();
    }

    public static void navigateToActivity(Activity activity, Class<?> target) {
        navigateToActivity(activity, target, null);
    }

    public static void showToast(Context activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void enableBackButton(AppCompatActivity activity, MaterialToolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIconTint(
                ContextCompat.getColor(activity, android.R.color.black)
        );
        toolbar.setNavigationOnClickListener(v ->
                activity.getOnBackPressedDispatcher().onBackPressed()
        );
    }

    public static void enableBackButtonWithTitle(AppCompatActivity activity, MaterialToolbar toolbar, String title) {
        activity.setSupportActionBar(toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationIconTint(
                ContextCompat.getColor(activity, android.R.color.black)
        );
        toolbar.setTitle(title);
        toolbar.setNavigationOnClickListener(v ->
                activity.getOnBackPressedDispatcher().onBackPressed()
        );
    }

    public static void setupToolbar(AppCompatActivity activity, int id) {
        MaterialToolbar toolbar = activity.findViewById(id);
        ActivityExtensions.enableBackButton(activity, toolbar);
        ActivityExtensions.setStatusBarColor(activity);
    }

    public static void showErrorSnackBar(Activity activity, String msg) {
       var snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT);
       snackbar.setBackgroundTint(activity.getColor(R.color.colorError));
       snackbar.setTextColor(activity.getColor(R.color.colorOnPrimary));
       snackbar.show();
    }

    public static void showSuccessSnackBar(Activity activity, String msg) {
        var snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(activity.getColor(R.color.colorSuccess));
        snackbar.setTextColor(activity.getColor(R.color.colorOnPrimary));
        snackbar.show();
    }
}