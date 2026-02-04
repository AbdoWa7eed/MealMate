package com.iti.mealmate.ui.auth.login;

import com.iti.mealmate.core.base.BasePresenter;

public interface LoginPresenter extends BasePresenter {

    void login(String email, String password);

    void loginWithGoogle();

    void loginWithFacebook();
}
