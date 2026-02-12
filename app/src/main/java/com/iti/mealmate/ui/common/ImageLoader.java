package com.iti.mealmate.ui.common;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.iti.mealmate.R;

public class ImageLoader {

    private static final int DEFAULT_PLACEHOLDER = R.drawable.meal_image_placeholder;
    private ImageLoader() {}

    public static void loadMealImage(Context context, String url, ImageView imageView) {
        loadWithPlaceholder(context, url, imageView, DEFAULT_PLACEHOLDER, DEFAULT_PLACEHOLDER);
    }


    public static void loadWithPlaceholder(Context context, String url, ImageView imageView, @DrawableRes int placeholder, @DrawableRes int error) {
        if (isValid(context, url)) {
            Glide.with(context)
                    .load(url)
                    .error(error)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } else {
            imageView.setImageResource(error);
        }
    }

    public static void loadCircular(Context context, Uri uri, ImageView imageView, @DrawableRes int error) {
        if (context != null && uri != null) {
            Glide.with(context)
                    .load(uri)
                    .circleCrop()
                    .error(error)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } else {
            imageView.setImageResource(error);
        }
    }

    private static boolean isValid(Context context, String url) {
        return context != null && url != null && !url.isEmpty();
    }
}
