package com.iti.mealmate.ui.profile;

import android.net.Uri;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.auth.model.UserModel;

public interface ProfileView extends BaseView {
    void showUserProfile(UserModel user);
    void showLastSyncTime(String formattedTime);
    void setLoading(LoadingType type, boolean isLoading);
    void setUpdateImageButtonVisible(boolean visible);
    void openImagePicker();
    void showPickedImage(Uri imageUri);
    void showOriginalImage(String imageUrl);
    void setEditIconState(boolean isImagePicked);
    void navigateToLogin();
    void showGuestMode();

    enum LoadingType {
        SYNC, IMAGE_UPLOAD, LOGOUT
    }
}