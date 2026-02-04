package com.iti.mealmate.ui.auth.register;

import com.iti.mealmate.core.base.BasePresenter;

public interface RegistrationPresenter extends BasePresenter {
    void register(String name, String email, String password, String confirmPassword);
}
