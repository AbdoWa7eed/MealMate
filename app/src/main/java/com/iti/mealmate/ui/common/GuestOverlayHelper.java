package com.iti.mealmate.ui.common;
import android.view.View;
import android.content.Context;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.google.android.material.button.MaterialButton;
import com.iti.mealmate.R;
import com.iti.mealmate.ui.auth.AuthenticationActivity;

public class GuestOverlayHelper {

    public static void showGuestOverlay(@NonNull View rootView, @NonNull Activity activity) {
        View overlay = rootView.findViewById(R.id.guest_overlay);
        if (overlay == null) return;

        MaterialButton loginButton = overlay.findViewById(R.id.btn_guest_login);
        if (loginButton != null) {
            loginButton.setOnClickListener(v -> {
                ActivityExtensions.navigateAndFinish(activity, AuthenticationActivity.class);

            });
        }

        overlay.setVisibility(View.VISIBLE);
    }

    public static void hideGuestOverlay(@NonNull View overlayRoot) {
        overlayRoot.setVisibility(View.GONE);
    }
}
