package com.iti.mealmate.ui.auth.register.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.databinding.FragmentRegistrationBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.auth.register.RegistrationPresenter;
import com.iti.mealmate.ui.auth.register.RegistrationView;
import com.iti.mealmate.ui.auth.register.presenter.RegistrationPresenterImpl;

import java.util.Objects;


public class RegistrationFragment extends Fragment implements RegistrationView {


    private FragmentRegistrationBinding binding;
    private RegistrationPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        presenter = new RegistrationPresenterImpl(this, ServiceLocator.getAuthRepository());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loginLink.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.registerButton.setOnClickListener(v -> {
            String name = Objects.requireNonNull(binding.fullNameInput.getText()).toString().trim();
            String email = Objects.requireNonNull(binding.emailInput.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordInput.getText()).toString().trim();
            String confirmPassword = Objects.requireNonNull(binding.confirmPasswordInput.getText()).toString().trim();
            presenter.register(name, email, password, confirmPassword);
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void navigateToHome(UserModel user) {
        Toast.makeText(requireContext(), "Welcome " + user.getName(), Toast.LENGTH_SHORT).show();
        // TODO: Navigate to Home Activity or Fragment
    }

    @Override
    public void showNameError(String message) {
        binding.fullNameInput.setError(message);
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
    public void showConfirmPasswordError(String message) {
        binding.confirmPasswordInput.setError(message);
    }

    @Override
    public void showLoading() {
        // TODO: Show loading indicator
    }

    @Override
    public void hideLoading() {
        // TODO: Hide loading indicator
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
