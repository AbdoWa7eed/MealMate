package com.iti.mealmate.ui.profile.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.iti.mealmate.R;
import com.iti.mealmate.core.util.AppUtils;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.databinding.FragmentProfileBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.auth.AuthenticationActivity;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.profile.ProfileView;
import com.iti.mealmate.ui.profile.presenter.ProfilePresenterImpl;

public class ProfileFragment extends Fragment implements ProfileView {

    private FragmentProfileBinding binding;
    private ProfilePresenterImpl presenter;
    private ProfileUiStateHandler uiStateHandler;
    private androidx.activity.result.ActivityResultLauncher<androidx.activity.result.PickVisualMediaRequest> pickMedia;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickMedia = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        presenter.onImagePicked(uri);
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiStateHandler = new ProfileUiStateHandler(binding);

        presenter = new ProfilePresenterImpl(
                this,
                ServiceLocator.getAuthRepository(),
                ServiceLocator.getProfileRepository()
        );

        setupClickListeners();
        presenter.onViewCreated();
    }

    private void setupClickListeners() {
        binding.btnLogout.setOnClickListener(v -> presenter.logout());
        binding.btnSync.setOnClickListener(v -> presenter.syncData());
        binding.btnEditProfileImage.setOnClickListener(v -> presenter.onEditIconClicked());
        binding.btnUpdateProfileImage.setOnClickListener(v -> presenter.updateProfileImage());
    }

    @Override
    public void openImagePicker() {
        pickMedia.launch(new androidx.activity.result.PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    @Override
    public void showUserProfile(UserModel user) {
        uiStateHandler.showContent();
        binding.textUserName.setText(user.getName());
        binding.textUserEmail.setText(user.getEmail());
        binding.textSavedPlansCount.setText(String.valueOf(user.getPlannedMealsCount()));
        binding.textFavoritePlansCount.setText(String.valueOf(user.getFavoriteMealsCount()));
        binding.textVersion.setText(AppUtils.getAppVersion(requireContext()));
        loadProfileImage(user.getImageUrl());
    }

    @Override
    public void showPickedImage(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .error(R.drawable.onboarding_image_1)
                .into(binding.imageProfile);
    }

    @Override
    public void showOriginalImage(String imageUrl) {
        loadProfileImage(imageUrl);
    }

    private void loadProfileImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .error(R.drawable.onboarding_image_1)
                    .into(binding.imageProfile);
        } else {
            binding.imageProfile.setImageResource(R.drawable.onboarding_image_1);
        }
    }

    @Override
    public void setEditIconState(boolean isImagePicked) {
        uiStateHandler.setEditIconState(isImagePicked);
    }

    @Override
    public void showLastSyncTime(String formattedTime) {
        binding.textSyncSubtitle.setText(getString(R.string.last_synced, formattedTime));
    }

    @Override
    public void setLoading(LoadingType type, boolean isLoading) {
        uiStateHandler.setLoading(type, isLoading);
    }

    @Override
    public void setUpdateImageButtonVisible(boolean visible) {
        uiStateHandler.setUpdateImageButtonVisible(visible);
    }

    @Override
    public void navigateToLogin() {
        ActivityExtensions.navigateAndFinish(requireActivity(), AuthenticationActivity.class);
    }

    @Override
    public void showLoading() {
        uiStateHandler.showLoading();
    }

    @Override
    public void hideLoading() {
        uiStateHandler.showContent();
    }

    @Override
    public void showErrorMessage(String message) {
        ActivityExtensions.showErrorSnackBar(requireActivity(), message);
    }

    @Override
    public void showPageError(String message) {
        ActivityExtensions.showErrorSnackBar(requireActivity(), message);
    }

    @Override
    public void showSuccessMessage(String message) {
        ActivityExtensions.showSuccessSnackBar(requireActivity(), message);
    }

    @Override
    public void noInternetError() {
        ActivityExtensions.showErrorSnackBar(requireActivity(), getString(R.string.error_network_subtitle));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
        binding = null;
    }
}