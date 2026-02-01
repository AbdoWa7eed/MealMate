package com.iti.mealmate.ui.auth.register;

import com.iti.mealmate.base.BaseView;
import com.iti.mealmate.data.auth.model.UserModel;

public interface RegistrationView extends BaseView {
    void navigateToHome(UserModel user);
    void showNameError(String message);
    void showEmailError(String message);
    void showPasswordError(String message);
    void showConfirmPasswordError(String message);
}
