package com.iti.mealmate.ui.auth.login.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iti.mealmate.R;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.databinding.FragmentLoginBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.auth.login.LoginPresenter;
import com.iti.mealmate.ui.auth.login.LoginView;
import com.iti.mealmate.ui.auth.login.presenter.LoginPresenterImpl;

import java.util.Objects;


public class LoginFragment extends Fragment implements LoginView {


    private FragmentLoginBinding binding;

    private LoginPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        presenter = new LoginPresenterImpl(this, ServiceLocator.getAuthRepository());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.signupLink.setOnClickListener(v -> navigateToRegistration());
        binding.loginButton.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.emailInput.getText()).toString();
            String password = Objects.requireNonNull(binding.passwordInput.getText()).toString();
            presenter.login(email, password);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void navigateToRegistration() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrationFragment);
    }

    @Override
    public void navigateToHome(UserModel user) {
        Toast.makeText(requireContext(), "User: " + user.getEmail(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showEmailError(String message) {
        binding.emailInput.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        binding.passwordInput.setError(message);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}