package com.iti.mealmate.data.auth.model;

import java.util.Arrays;

public enum AuthProvider {
    GOOGLE,
    FACEBOOK,
    BASIC;


    public static AuthProvider fromString(String provider) {
        return Arrays.stream(AuthProvider.values())
                .filter(p -> p.name().equalsIgnoreCase(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown provider: " + provider));
    }
}

