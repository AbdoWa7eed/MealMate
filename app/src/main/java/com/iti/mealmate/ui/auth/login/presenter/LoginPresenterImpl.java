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
        if (email.isEmpty()) {
            view.showEmailError("Email cannot be empty");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Enter a valid email");
            return;
        }

        if (password.isEmpty()) {
            view.showPasswordError("Password cannot be empty");
            return;
        }

        if (password.length() < 6) {
            view.showPasswordError("Password must be at least 6 characters");
            return;
        }

        view.showLoading();
        Disposable disposable = repository.loginWithEmail(new LoginRequest(email , password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::navigateToHome, throwable -> {
                    view.hideLoading();
                    view.showError(throwable.getMessage());
                });

        compositeDisposable.add(disposable);

    }

    @Override
    public void loginWithGoogle() {

    }

    @Override
    public void loginWithFacebook() {

    }
}
