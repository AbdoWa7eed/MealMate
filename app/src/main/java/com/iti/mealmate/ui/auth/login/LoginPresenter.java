package com.iti.mealmate.ui.auth.login;

import com.iti.mealmate.data.auth.model.LoginRequest;

public interface LoginPresenter {

    void login(String email, String password);

    void loginWithGoogle();

    void loginWithFacebook();
}
