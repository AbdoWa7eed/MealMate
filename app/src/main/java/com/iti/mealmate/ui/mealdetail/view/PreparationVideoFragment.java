package com.iti.mealmate.ui.mealdetail.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.mealmate.core.util.YouTubeUtils;
import com.iti.mealmate.databinding.FragmentPreparationVideoBinding;
import com.iti.mealmate.ui.common.ActivityExtensions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class PreparationVideoFragment extends Fragment {

    private static final String ARG_VIDEO_URL = "video_url";
    public static final String TAG = "PreparationVideoFragment";
    private FragmentPreparationVideoBinding binding;
    private String videoUrl;

    public static PreparationVideoFragment newInstance(String videoUrl) {
        PreparationVideoFragment fragment = new PreparationVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_URL, videoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoUrl = getArguments().getString(ARG_VIDEO_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPreparationVideoBinding.inflate(inflater, container, false);
        setupToolbar();
        setupYouTubePlayer();
        return binding.getRoot();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );
        ActivityExtensions.setStatusBarDarkWithLightIcons(requireActivity());
    }

    private void setupYouTubePlayer() {
        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                loadVideo(youTubePlayer);
            }
        });
        binding.youtubePlayerView.addFullscreenListener(new FullscreenListener() {
            @Override
            public void onEnterFullscreen(@NonNull View view, @NonNull Function0<Unit> function0) {
                binding.toolbar.setVisibility(View.GONE);
                ActivityExtensions.hideSystemUI(getActivity(), binding.getRoot());
            }

            @Override
            public void onExitFullscreen() {
                binding.toolbar.setVisibility(View.VISIBLE);
                ActivityExtensions.showSystemUI(getActivity(), binding.getRoot());
            }
        });
    }

    private void loadVideo(YouTubePlayer youTubePlayer) {
        if (videoUrl == null || videoUrl.isEmpty()) return;
        String videoId = YouTubeUtils.extractVideoId(videoUrl);
        if (videoId != null) {
            youTubePlayer.loadVideo(videoId, 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null) {
            ActivityExtensions.setStatusBarTransparent(getActivity());
        }
        binding = null;
    }
}
