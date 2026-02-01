package com.iti.mealmate.ui.auth.register.presenter;

import android.util.Patterns;

import com.iti.mealmate.data.auth.model.RegisterRequest;
import com.iti.mealmate.data.auth.repo.AuthRepository;
import com.iti.mealmate.ui.auth.register.RegistrationPresenter;
import com.iti.mealmate.ui.auth.register.RegistrationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegistrationPresenterImpl implements RegistrationPresenter {

    private final RegistrationView view;
    private final AuthRepository repository;
    private final CompositeDisposable compositeDisposable;

    public RegistrationPresenterImpl(RegistrationView view, AuthRepository repository) {
        this.view = view;
        this.repository = repository;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void register(String name, String email, String password, String confirmPassword) {
        if (!validateAllFields(name, email, password, confirmPassword)) {
            return;
        }
        performRegistration(new RegisterRequest(name, email, password));
    }

    private void performRegistration(RegisterRequest request) {
        view.showLoading();
        Disposable disposable = repository.registerWithEmail(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    view.hideLoading();
                    view.navigateToHome(user);
                }, throwable -> {
                    view.hideLoading();
                    view.showError(throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    private boolean validateAllFields(String name, String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (name.isEmpty()) {
            view.showNameError("Name cannot be empty");
            isValid = false;
        }

        if (email.isEmpty()) {
            view.showEmailError("Email cannot be empty");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

        if (confirmPassword.isEmpty()) {
            view.showConfirmPasswordError("Confirm password cannot be empty");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            view.showConfirmPasswordError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
