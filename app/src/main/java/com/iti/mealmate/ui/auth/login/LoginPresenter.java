package com.iti.mealmate.ui.auth.login;

import com.iti.mealmate.base.BasePresenter;
import com.iti.mealmate.data.auth.model.LoginRequest;

public interface LoginPresenter extends BasePresenter {

    void login(String email, String password);

    void loginWithGoogle();

    void loginWithFacebook();
}
