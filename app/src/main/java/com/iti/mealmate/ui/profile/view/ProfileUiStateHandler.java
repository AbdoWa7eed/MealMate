package com.iti.mealmate.ui.profile.view;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.FragmentProfileBinding;
import com.iti.mealmate.ui.profile.ProfileView;

public class ProfileUiStateHandler {

    private final FragmentProfileBinding binding;
    private final Context context;

    public ProfileUiStateHandler(FragmentProfileBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
    }

    public void showLoading() {
        binding.viewShimmer.shimmerProfileContainer.startShimmer();
        binding.viewShimmer.shimmerProfileContainer.setVisibility(View.VISIBLE);

        binding.layoutProfileImage.setVisibility(View.INVISIBLE);
        binding.textUserName.setVisibility(View.INVISIBLE);
        binding.textUserEmail.setVisibility(View.INVISIBLE);
        binding.layoutStatsCard.setVisibility(View.INVISIBLE);
        binding.btnSync.setVisibility(View.INVISIBLE);
        binding.btnLogout.setVisibility(View.INVISIBLE);
        binding.textVersion.setVisibility(View.INVISIBLE);
    }

    public void showContent() {
        binding.viewShimmer.shimmerProfileContainer.stopShimmer();
        binding.viewShimmer.shimmerProfileContainer.setVisibility(View.GONE);

        binding.layoutProfileImage.setVisibility(View.VISIBLE);
        binding.textUserName.setVisibility(View.VISIBLE);
        binding.textUserEmail.setVisibility(View.VISIBLE);
        binding.layoutStatsCard.setVisibility(View.VISIBLE);
        binding.btnSync.setVisibility(View.VISIBLE);
        binding.btnLogout.setVisibility(View.VISIBLE);
        binding.textVersion.setVisibility(View.VISIBLE);
    }

    public void setLoading(ProfileView.LoadingType type, boolean isLoading) {
        switch (type) {
            case SYNC:
                handleSyncLoading(isLoading);
                break;
            case IMAGE_UPLOAD:
                handleImageUploadLoading(isLoading);
                break;
            case LOGOUT:
                handleLogoutLoading(isLoading);
                break;
        }
    }

    private void handleSyncLoading(boolean isLoading) {
        setButtonsEnabled(!isLoading);

        if (isLoading) {
            Animation rotation = AnimationUtils.loadAnimation(context, R.anim.rotate_sync);
            binding.iconSync.startAnimation(rotation);
            binding.textSyncSubtitle.setText(R.string.syncing);
        } else {
            binding.iconSync.clearAnimation();
        }
    }

    private void handleLogoutLoading(boolean isLoading) {
        setButtonsEnabled(!isLoading);

        if (isLoading) {
            binding.iconLogout.setVisibility(View.INVISIBLE);
            binding.progressLogout.setVisibility(View.VISIBLE);
        } else {
            binding.iconLogout.setVisibility(View.VISIBLE);
            binding.progressLogout.setVisibility(View.GONE);
        }
    }

    private void handleImageUploadLoading(boolean isLoading) {
        setButtonsEnabled(!isLoading);

        if (isLoading) {
            binding.iconEditProfile.setVisibility(View.INVISIBLE);
            binding.progressEditProfile.setVisibility(View.VISIBLE);
        } else {
            binding.iconEditProfile.setVisibility(View.VISIBLE);
            binding.progressEditProfile.setVisibility(View.GONE);
        }
    }

    public void setEditIconState(boolean isImagePicked) {
        if (isImagePicked) {
            binding.iconEditProfile.setImageResource(R.drawable.ic_close);
        } else {
            binding.iconEditProfile.setImageResource(R.drawable.ic_edit);
        }
    }

    public void setUpdateImageButtonVisible(boolean visible) {
        binding.btnUpdateProfileImage.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setButtonsEnabled(boolean enabled) {
        binding.btnSync.setEnabled(enabled);
        binding.btnLogout.setEnabled(enabled);
        binding.btnUpdateProfileImage.setEnabled(enabled);
        binding.btnEditProfileImage.setEnabled(enabled);
    }
}