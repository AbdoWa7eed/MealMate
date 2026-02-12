package com.iti.mealmate.ui.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import com.iti.mealmate.databinding.DialogConfirmationBinding;

import java.util.function.Consumer;

public class DialogUtils {

    private DialogUtils() {}

    public static void showConfirmationDialog(
            @NonNull Context context,
            @NonNull String title,
            @NonNull String message,
            @NonNull Consumer<Void> onConfirm
    ) {
        DialogConfirmationBinding binding = DialogConfirmationBinding.inflate(
                LayoutInflater.from(context)
        );

        Dialog dialog = createDialog(context, binding);
        setupDialogContent(binding, title, message);
        setupDialogActions(binding, dialog, onConfirm);

        dialog.show();
        animateDialogEntry(binding.dialogCard);
    }

    private static Dialog createDialog(Context context, DialogConfirmationBinding binding) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        configureDialogWindow(dialog.getWindow());
        return dialog;
    }

    private static void configureDialogWindow(Window window) {
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }

    private static void setupDialogContent(
            DialogConfirmationBinding binding,
            String title,
            String message
    ) {
        binding.textDialogTitle.setText(title);
        binding.textDialogMessage.setText(message);
    }

    private static void setupDialogActions(
            DialogConfirmationBinding binding,
            Dialog dialog,
            Consumer<Void> onConfirm
    ) {
        binding.btnSure.setOnClickListener(v -> {
            onConfirm.accept(null);
            dialog.dismiss();
        });

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss());
    }

    private static void animateDialogEntry(CardView card) {
        card.setScaleX(0.8f);
        card.setScaleY(0.8f);
        card.setAlpha(0f);

        card.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }
}