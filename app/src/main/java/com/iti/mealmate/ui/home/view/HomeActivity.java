package com.iti.mealmate.ui.home.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.iti.mealmate.R;
import com.iti.mealmate.databinding.ActivityHomeBinding;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.iti.mealmate.ui.discover.view.DiscoverFragment;
import com.iti.mealmate.ui.favorites.view.FavoritesFragment;
import com.iti.mealmate.ui.plan.view.PlanFragment;
import com.iti.mealmate.ui.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    private Fragment homeFragment;
    private Fragment discoverFragment;
    private Fragment planFragment;
    private Fragment favoritesFragment;
    private Fragment profileFragment;

    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState != null) {
            restoreFragments();
        } else {
            homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.home_nav_host, homeFragment)
                    .commit();
            activeFragment = homeFragment;
        }

        setupBottomNavigation();
        ActivityExtensions.setStatusBarWithDarkIcons(this);
    }

    private void restoreFragments() {
        homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        discoverFragment = getSupportFragmentManager().findFragmentByTag(DiscoverFragment.class.getSimpleName());
        planFragment = getSupportFragmentManager().findFragmentByTag(PlanFragment.class.getSimpleName());
        favoritesFragment = getSupportFragmentManager().findFragmentByTag(FavoritesFragment.class.getSimpleName());
        profileFragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
        Fragment current = getSupportFragmentManager().getPrimaryNavigationFragment();
        activeFragment = current != null ? current : homeFragment;
    }

    private void setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener(item -> {
            switchFragment(getFragmentForMenu(item.getItemId()));
            return true;
        });
    }

    private Fragment getFragmentForMenu(int menuId) {
        if (menuId == R.id.homeFragment) {
            if (homeFragment == null) homeFragment = new HomeFragment();
            return homeFragment;
        } else if (menuId == R.id.discoverFragment) {
            if (discoverFragment == null) discoverFragment = new DiscoverFragment();
            return discoverFragment;
        } else if (menuId == R.id.planFragment) {
            if (planFragment == null) planFragment = new PlanFragment();
            return planFragment;
        } else if (menuId == R.id.favoritesFragment) {
            if (favoritesFragment == null) favoritesFragment = new FavoritesFragment();
            return favoritesFragment;
        } else if (menuId == R.id.profileFragment) {
            if (profileFragment == null) profileFragment = new ProfileFragment();
            return profileFragment;
        } else {
            return null;
        }
    }

    private void switchFragment(Fragment target) {
        if (target == null || target == activeFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (!target.isAdded()) {
            String tag = target.getClass().getSimpleName();
            transaction.add(R.id.home_nav_host, target, tag);
        }

        if (activeFragment != null) {
            transaction.hide(activeFragment);
        }

        transaction.show(target).commit();

        activeFragment = target;
    }


}
