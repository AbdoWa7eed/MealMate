package com.iti.mealmate.ui.auth.login;

import com.iti.mealmate.core.base.BaseView;
import com.iti.mealmate.data.auth.model.UserModel;

public interface LoginView extends BaseView {
    void navigateToHome();
    void showEmailError(String message);
    void showPasswordError(String message);

}
