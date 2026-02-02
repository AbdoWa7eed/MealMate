package com.iti.mealmate.ui.auth.login.presenter;

import android.content.Context;
import android.util.Log;

import com.iti.mealmate.data.auth.model.LoginRequest;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.ui.auth.login.LoginPresenter;
import com.iti.mealmate.ui.auth.login.LoginView;
import com.iti.mealmate.util.GoogleSignInHelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView view;

    private final AuthRepository repository;

    private final CompositeDisposable compositeDisposable;

    private final Context context;

    public LoginPresenterImpl(Context context, LoginView view, AuthRepository repository) {
        this.context = context;
        this.view = view;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void login(String email, String password) {
        if (!validateInputs(email, password)) {
            return;
        }

        performLogin(email, password);
    }


    private void performLogin(String email, String password) {
        view.showLoading();
        var loginWithEmailRequset = repository
                .loginWithEmail(new LoginRequest(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        compositeDisposable.add(loginWithEmailRequset);
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty()) {
            view.showEmailError("Email cannot be empty");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Enter a valid email");
            isValid = false;
        }

        if (password.isEmpty()) {
            view.showPasswordError("Password cannot be empty");
            isValid = false;
        } else if (password.length() < 6) {
            view.showPasswordError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void loginWithGoogle() {
        var googleTokenRequest = GoogleSignInHelper.signIn(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeSignIn,
                        error -> view.showError(error.getMessage()));
        compositeDisposable.add(googleTokenRequest);
    }

    private void completeSignIn(String idToken) {
        view.showLoading();
        var googleSignInRequest = repository
                .signInWithGoogle(idToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);

        compositeDisposable.add(googleSignInRequest);
    }

    @Override
    public void loginWithFacebook() {
        // TODO: Implement Facebook login
    }

    private void onError(Throwable error) {
        view.hideLoading();
        view.showError(error.getMessage());
    }


    private void onSuccess(UserModel userModel) {
        view.navigateToHome(userModel);
        view.hideLoading();
    }


    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
