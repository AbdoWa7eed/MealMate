package com.iti.mealmate;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.iti.mealmate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        startAnimations();
    }

    private void startAnimations() {
        Animation cardAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_card_scale);
        binding.imageCard.startAnimation(cardAnimation);

        AnimatedVectorDrawable animatedDrawable =
                (AnimatedVectorDrawable) ContextCompat.getDrawable(this, R.drawable.logo_animated);
        binding.logoImage.setImageDrawable(animatedDrawable);
        if (animatedDrawable != null) {
            animatedDrawable.start();
        }

        Animation titleAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_title_slide);
        binding.tvTitle.startAnimation(titleAnimation);

        Animation sloganAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slogan_slide);
        binding.tvSlogan.startAnimation(sloganAnimation);
    }
}
