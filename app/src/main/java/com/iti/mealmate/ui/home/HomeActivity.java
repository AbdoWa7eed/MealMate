package com.iti.mealmate.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.iti.mealmate.databinding.ActivityHomeBinding;
import com.iti.mealmate.R;
import com.iti.mealmate.ui.utils.ActivityExtensions;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewBinding();
        setSupportActionBar(binding.homeToolbar);
        ActivityExtensions.setStatusBarWithDarkIcons(this);
        setupNavigation();
    }

    private void setupViewBinding() {
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupNavigation() {
        binding.homeNavHost.post(() -> {
            navController = androidx.navigation.Navigation.findNavController(binding.homeNavHost);
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.homeFragment,
                    R.id.discoverFragment,
                    R.id.planFragment,
                    R.id.favoritesFragment,
                    R.id.profileFragment
            ).build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.bottomNav, navController);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (navController != null && appBarConfiguration != null) {
            return NavigationUI.navigateUp(navController, appBarConfiguration)
                    || super.onSupportNavigateUp();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
