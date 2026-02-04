package com.iti.mealmate.ui.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.iti.mealmate.ui.home.view.HomeShimmerFragment;

public class FragmentStateManager {

    private static final String TAG_SHIMMER = "home_shimmer_fragment";
    private static final String TAG_ERROR = "error_fragment";

    public static void showShimmerFragment(@NonNull FragmentManager fragmentManager, int containerId) {
        if (!fragmentManager.isStateSaved()) {
            Fragment existingShimmer = fragmentManager.findFragmentByTag(TAG_SHIMMER);
            if (existingShimmer == null) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(containerId, new HomeShimmerFragment(), TAG_SHIMMER);
                transaction.commitAllowingStateLoss();
            } else if (!existingShimmer.isVisible()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.show(existingShimmer);
                transaction.commitAllowingStateLoss();
            }
        }
    }

    public static void showErrorFragment(@NonNull FragmentManager fragmentManager, int containerId,
                                         @NonNull String title,
                                         @Nullable Runnable retryAction) {
        if (!fragmentManager.isStateSaved()) {
            ErrorFragment errorFragment = ErrorFragment.newInstance(title, retryAction != null);
            errorFragment.setRetryAction(retryAction);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(containerId, errorFragment, TAG_ERROR);
            transaction.commitAllowingStateLoss();
        }
    }

    public static void showConnectionErrorFragment(@NonNull FragmentManager fragmentManager, int containerId,
                                                   @Nullable Runnable retryAction) {
        if (!fragmentManager.isStateSaved()) {
            ErrorFragment errorFragment =
                    ErrorFragment.newConnectionErrorInstance(retryAction != null);
            errorFragment.setRetryAction(retryAction);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(containerId, errorFragment, TAG_ERROR);
            transaction.commitAllowingStateLoss();
        }
    }


    public static void hideShimmerFragment(@NonNull FragmentManager fragmentManager) {
        if (!fragmentManager.isStateSaved()) {
            Fragment shimmerFragment = fragmentManager.findFragmentByTag(TAG_SHIMMER);
            if (shimmerFragment != null && shimmerFragment.isVisible()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(shimmerFragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }


    public static void hideErrorFragment(@NonNull FragmentManager fragmentManager) {
        if (!fragmentManager.isStateSaved()) {
            Fragment errorFragment = fragmentManager.findFragmentByTag(TAG_ERROR);
            if (errorFragment != null && errorFragment.isVisible()) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(errorFragment);
                transaction.commitAllowingStateLoss();
            }
        }
    }


    public static void hideStateFragment(@NonNull FragmentManager fragmentManager) {
        hideShimmerFragment(fragmentManager);
        hideErrorFragment(fragmentManager);
    }
}
