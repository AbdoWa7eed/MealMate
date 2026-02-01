package com.iti.mealmate.ui.auth.login.presenter;

import com.iti.mealmate.data.auth.model.LoginRequest;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.ui.auth.login.LoginPresenter;
import com.iti.mealmate.ui.auth.login.LoginView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter {

    private final LoginView view;

    private final AuthRepository repository;

    private final CompositeDisposable compositeDisposable;

    public LoginPresenterImpl(LoginView view, AuthRepository repository) {
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
        Disposable disposable = repository.loginWithEmail(new LoginRequest(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    view.hideLoading();
                    view.navigateToHome(userModel);
                }, throwable -> {
                    view.hideLoading();
                    view.showError(throwable.getMessage());
                });
        compositeDisposable.add(disposable);
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
        // TODO: Implement Google login
    }

    @Override
    public void loginWithFacebook() {
        // TODO: Implement Facebook login
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
