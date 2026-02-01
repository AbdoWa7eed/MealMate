package com.iti.mealmate.ui.auth.register;

public interface RegistrationPresenter {
    void register(String name, String email, String password, String confirmPassword);
}
