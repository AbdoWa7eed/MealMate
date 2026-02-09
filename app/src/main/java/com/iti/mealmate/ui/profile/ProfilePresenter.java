package com.iti.mealmate.ui.profile;

import android.net.Uri;

public interface ProfilePresenter {
    void onViewCreated();
    void loadUserProfile();
    void logout();
    void syncData();
    void onImagePicked(Uri imageUri);
    void updateProfileImage();
    void onEditIconClicked();
    void onDestroy();
}