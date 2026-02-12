package com.iti.mealmate.ui.auth;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.iti.mealmate.R;
import com.iti.mealmate.ui.common.ActivityExtensions;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);
        ActivityExtensions.setStatusBarWithDarkIcons(this);

    }
}