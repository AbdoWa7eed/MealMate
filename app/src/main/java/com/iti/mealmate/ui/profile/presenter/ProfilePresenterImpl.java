package com.iti.mealmate.ui.profile.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.mealmate.core.network.NoConnectivityException;
import com.iti.mealmate.core.util.DateUtils;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.data.meal.repo.sync.SyncRepository;
import com.iti.mealmate.data.profile.repo.ProfileRepository;
import com.iti.mealmate.data.source.local.db.AppDatabase;
import com.iti.mealmate.data.source.local.prefs.PreferencesHelper;
import com.iti.mealmate.ui.profile.ProfilePresenter;
import com.iti.mealmate.ui.profile.ProfileView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.net.Uri;


public class ProfilePresenterImpl implements ProfilePresenter {

    private final ProfileView view;
    private final AuthRepository authRepository;
    private final ProfileRepository profileRepository;
    private final PreferencesHelper preferencesHelper;

    private final SyncRepository syncRepository;
    private final AppDatabase appDatabase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private UserModel currentUser;
    private Uri selectedImageUri;
    private boolean isImagePicked = false;

    public ProfilePresenterImpl(ProfileView view,
                                AuthRepository authRepository,
                                ProfileRepository profileRepository,
                                PreferencesHelper preferencesHelper, SyncRepository syncRepository,
                                AppDatabase appDatabase) {
        this.view = view;
        this.authRepository = authRepository;
        this.profileRepository = profileRepository;
        this.preferencesHelper = preferencesHelper;
        this.syncRepository = syncRepository;
        this.appDatabase = appDatabase;
    }

    @Override
    public void onViewCreated() {
        loadUserProfile();
    }

    @Override
    public void loadUserProfile() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            view.navigateToLogin();
            return;
        }

        view.showLoading();

        disposables.add(profileRepository.getUserProfile(firebaseUser.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUserProfileLoaded, this::onError));
    }

    private void onUserProfileLoaded(UserModel user) {
        this.currentUser = user;
        view.hideLoading();
        view.showUserProfile(user);
        view.showLastSyncTime(DateUtils.formatSyncDate(user.getLastSyncedDate()));
    }

    private void onError(Throwable throwable) {
        if (throwable instanceof NoConnectivityException) {
            view.noInternetError();
        } else {
            view.showPageError(throwable.getMessage());
        }
        view.hideLoading();
    }

    @Override
    public void logout() {
        view.setLoading(ProfileView.LoadingType.LOGOUT, true);
        disposables.add(authRepository.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLogoutSuccess, this::onLogoutError));
    }

    private void onLogoutSuccess() {
        view.setLoading(ProfileView.LoadingType.LOGOUT, false);
        preferencesHelper.setUserId(null);
        disposables.add(
                Completable.fromAction(appDatabase::clearAllTables)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::navigateToLogin,
                                throwable -> view.showErrorMessage(throwable.getMessage())
                        )
        );
    }

    private void onLogoutError(Throwable throwable) {
        view.setLoading(ProfileView.LoadingType.LOGOUT, false);
        view.showErrorMessage(throwable.getMessage());
    }

    @Override
    public void syncData() {
        view.setLoading(ProfileView.LoadingType.SYNC, true);
        disposables.add(syncRepository.syncAll(currentUser.getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSyncSuccess, this::onSyncError));
    }

    private void onSyncSuccess() {
        loadUserProfile();
        view.setLoading(ProfileView.LoadingType.SYNC, false);
        view.showLastSyncTime(DateUtils.formatSyncDate(System.currentTimeMillis()));
        view.showSuccessMessage("Sync completed");
    }

    private void onSyncError(Throwable throwable) {
        view.setLoading(ProfileView.LoadingType.SYNC, false);
        view.showLastSyncTime(DateUtils.formatSyncDate(currentUser.getLastSyncedDate()));
        view.showErrorMessage(throwable.getMessage());
    }


    @Override
    public void onEditIconClicked() {
        if (isImagePicked) {
            cancelImageSelection();
        } else {
            view.openImagePicker();
        }
    }

    @Override
    public void onImagePicked(Uri imageUri) {
        this.selectedImageUri = imageUri;
        this.isImagePicked = true;
        view.showPickedImage(imageUri);
        view.setUpdateImageButtonVisible(true);
        view.setEditIconState(true);
    }

    private void cancelImageSelection() {
        this.selectedImageUri = null;
        this.isImagePicked = false;
        view.showOriginalImage(currentUser.getImageUrl());
        view.setUpdateImageButtonVisible(false);
        view.setEditIconState(false);
    }

    @Override
    public void updateProfileImage() {
        if (!canUpdateImage()) return;

        view.setLoading(ProfileView.LoadingType.IMAGE_UPLOAD, true);
        view.setUpdateImageButtonVisible(false);

        disposables.add(
                profileRepository.updateProfileImage(currentUser.getUid(), selectedImageUri)
                        .flatMap(url -> profileRepository.getUserProfile(currentUser.getUid()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onProfileUpdated, this::onProfileUpdateFailed)
        );
    }

    private boolean canUpdateImage() {
        return selectedImageUri != null && currentUser != null;
    }

    private void onProfileUpdated(UserModel user) {
        this.currentUser = user;
        this.selectedImageUri = null;
        this.isImagePicked = false;
        view.setLoading(ProfileView.LoadingType.IMAGE_UPLOAD, false);
        view.showUserProfile(user);
        view.setEditIconState(false);
        view.showSuccessMessage("Profile image updated");
    }

    private void onProfileUpdateFailed(Throwable throwable) {
        view.setLoading(ProfileView.LoadingType.IMAGE_UPLOAD, false);
        view.showOriginalImage(currentUser.getImageUrl());
        view.setUpdateImageButtonVisible(true);
        view.showErrorMessage(throwable.getMessage());
    }

    @Override
    public void onDestroy() {
        currentUser = null;
        disposables.clear();
    }
}