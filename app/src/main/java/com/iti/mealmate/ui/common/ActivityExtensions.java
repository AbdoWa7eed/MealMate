package com.iti.mealmate.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import com.iti.mealmate.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ActivityExtensions {
    
    public static void setStatusBarDarkWithLightIcons(Activity activity) {
        setStatusBarColor(activity, android.R.color.black, false);
    }
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

    public static void setStatusBarTransparent(Activity activity) {
        activity.getWindow().setStatusBarColor(activity.getColor(android.R.color.transparent));
        WindowInsetsControllerCompat insetsController =
                new WindowInsetsControllerCompat(activity.getWindow(), activity.getWindow().getDecorView());
        insetsController.setAppearanceLightStatusBars(true);
    }

    public static void hideSystemUI(Activity activity, View decorView) {
        if (activity != null && activity.getWindow() != null) {
            WindowInsetsControllerCompat controller =
                    new WindowInsetsControllerCompat(activity.getWindow(), decorView);
            controller.hide(WindowInsetsCompat.Type.systemBars());
            controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }
    }

    public static void showSystemUI(Activity activity, View decorView) {
        if (activity != null && activity.getWindow() != null) {
            WindowInsetsControllerCompat controller =
                    new WindowInsetsControllerCompat(activity.getWindow(), decorView);
            controller.show(WindowInsetsCompat.Type.systemBars());
        }
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



    public static void navigateToFragment(AppCompatActivity activity, int containerId, Fragment fragment, String tag) {
        activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_bottom,
                        R.anim.slide_out_bottom,
                        R.anim.slide_in_bottom,
                        R.anim.slide_out_bottom
                )
                .replace(containerId, fragment)
                .addToBackStack(tag)
                .commit();
    }

}