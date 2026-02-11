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
import com.iti.mealmate.ui.profile.view.ProfileFragment;

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

        if (savedInstanceState == null) {

            homeFragment = new HomeFragment();
            discoverFragment = new DiscoverFragment();
            planFragment = new PlanFragment();
            favoritesFragment = new FavoritesFragment();
            profileFragment = new ProfileFragment();

            activeFragment = homeFragment;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.home_nav_host, profileFragment, ProfileFragment.class.getSimpleName()).hide(profileFragment);
            transaction.add(R.id.home_nav_host, favoritesFragment, FavoritesFragment.class.getSimpleName()).hide(favoritesFragment);
            transaction.add(R.id.home_nav_host, planFragment, PlanFragment.class.getSimpleName()).hide(planFragment);
            transaction.add(R.id.home_nav_host, discoverFragment, DiscoverFragment.class.getSimpleName()).hide(discoverFragment);
            transaction.add(R.id.home_nav_host, homeFragment, HomeFragment.class.getSimpleName());

            transaction.commit();
        } else {
            restoreFragments();
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
        if (menuId == R.id.homeFragment) return homeFragment;
        if (menuId == R.id.discoverFragment) return discoverFragment;
        if (menuId == R.id.planFragment) return planFragment;
        if (menuId == R.id.favoritesFragment) return favoritesFragment;
        if (menuId == R.id.profileFragment) return profileFragment;
        return null;
    }

    private void switchFragment(Fragment target) {
        if (target == null || target == activeFragment) return;

        getSupportFragmentManager()
                .beginTransaction()
                .hide(activeFragment)
                .show(target)
                .commit();

        activeFragment = target;
    }




}
