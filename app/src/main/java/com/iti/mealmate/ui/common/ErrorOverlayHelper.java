package com.iti.mealmate.ui.common;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.iti.mealmate.R;

public class ErrorOverlayHelper {

    public static void showNetworkError(@NonNull View rootView, @Nullable Runnable retryAction) {
        View overlay = rootView.findViewById(R.id.error_overlay);
        if (overlay == null) return;
        showErrorInternalOnOverlay(
                overlay,
                R.string.error_network_title,
                R.string.error_network_subtitle,
                R.drawable.no_connection_image,
                retryAction
        );
    }

    public static void showError(@NonNull View rootView,
                                 @Nullable String errorMessage,
                                 @Nullable Runnable retryAction) {

        View overlay = rootView.findViewById(R.id.error_overlay);
        if (overlay == null) return;

        if (errorMessage == null || errorMessage.isEmpty()) {
            showErrorInternalOnOverlay(
                    overlay,
                    R.string.error_title_default,
                    R.string.error_subtitle_default,
                    R.drawable.error_image,
                    retryAction
            );
        } else {
            showErrorInternalOnOverlay(
                    overlay,
                    R.string.error_title_default,
                    errorMessage,
                    R.drawable.error_image,
                    retryAction
            );
        }
    }


    public static void hideError (@NonNull View overlayRoot) {
        overlayRoot.setVisibility(View.GONE);
    }

    private static void showErrorInternalOnOverlay(@NonNull View overlayRoot,
                                          int titleResId,
                                          @NonNull Object subtitle,
                                          int imageResId,
                                          @Nullable Runnable retryAction) {

        if (overlayRoot.getVisibility() == View.VISIBLE) {
            return;
        }

        View errorLayout = overlayRoot.findViewById(R.id.error_layout);
        if (errorLayout != null) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        TextView errorTitle = overlayRoot.findViewById(R.id.error_title);
        if (errorTitle != null) {
            errorTitle.setText(titleResId);
        }

        TextView errorSubtitle = overlayRoot.findViewById(R.id.error_subtitle);
        if (errorSubtitle != null) {
            if (subtitle instanceof String) {
                errorSubtitle.setText((String) subtitle);
            } else if (subtitle instanceof Integer) {
                errorSubtitle.setText((Integer) subtitle);
            }
        }

        ImageView errorImage = overlayRoot.findViewById(R.id.error_image);
        if (errorImage != null) {
            errorImage.setImageResource(imageResId);
        }

        MaterialButton retryButton = overlayRoot.findViewById(R.id.retry_button);
        if (retryButton != null) {
            if (retryAction != null) {
                retryButton.setVisibility(View.VISIBLE);
                retryButton.setOnClickListener(v -> {
                    hideError(overlayRoot);
                    retryAction.run();
                });
            } else {
                retryButton.setVisibility(View.GONE);
            }
        }

        overlayRoot.setVisibility(View.VISIBLE);
    }
}
