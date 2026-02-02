package com.iti.mealmate.ui.auth.login.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.mealmate.R;
import com.iti.mealmate.data.auth.model.UserModel;
import com.iti.mealmate.databinding.FragmentLoginBinding;
import com.iti.mealmate.di.ServiceLocator;
import com.iti.mealmate.ui.auth.login.LoginPresenter;
import com.iti.mealmate.ui.auth.login.LoginView;
import com.iti.mealmate.ui.auth.login.presenter.LoginPresenterImpl;
import com.iti.mealmate.ui.utils.ActivityExtensions;
import com.iti.mealmate.util.FacebookLoginProvider;

import java.util.Objects;



public class LoginFragment extends Fragment implements LoginView, FacebookLoginProvider {


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
        presenter = new LoginPresenterImpl(requireContext(), this, ServiceLocator.getAuthRepository());
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
        binding.googleButton.setOnClickListener(v -> presenter.loginWithGoogle());
        binding.facebookButton.setOnClickListener(v -> presenter.loginWithFacebook());
    }

    public void navigateToRegistration() {
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_registrationFragment);
    }

    @Override
    public void navigateToHome(UserModel user) {
        ActivityExtensions.showSuccessSnackBar(requireActivity(), "Welcome " + user.getName());
    }

    @Override
    public void showEmailError(String message) {
        binding.emailInputLayout.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        binding.passwordInputLayout.setError(message);
    }

    @Override
    public void showLoading() {
        binding.loginButton.setEnabled(false);
        binding.googleButton.setEnabled(false);
        binding.facebookButton.setEnabled(false);
        binding.loginButton.setText("");
        binding.loginProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.loginProgressBar.setVisibility(View.GONE);
        binding.googleButton.setEnabled(true);
        binding.facebookButton.setEnabled(true);
        binding.loginButton.setEnabled(true);
        binding.loginButton.setText(R.string.login);
    }

    @Override
    public void showError(String message) {
        ActivityExtensions.showErrorSnackBar(requireActivity(), message);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        binding = null;
    }

}