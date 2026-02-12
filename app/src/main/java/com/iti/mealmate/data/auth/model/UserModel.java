package com.iti.mealmate.data.auth.model;


public class UserModel {

    private String uid;
    private String email;
    private String name;
    private String imageUrl;

    private AuthProvider provider;
    private long createdAt;
    private long lastLogin;

    private long lastSyncedDate;

    private int favoriteMealsCount;

    private int plannedMealsCount;


    public UserModel() {
    }


    public UserModel(String uid,
                     String email,
                     String name,
                     String imageUrl,
                     AuthProvider provider,
                     long createdAt,
                     long lastLogin,
                     long lastSyncedDate,
                     int favoriteMealsCount,
                     int plannedMealsCount) {

        this.uid = uid;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.createdAt = createdAt;
        this.lastLogin = lastLogin;
        this.lastSyncedDate = lastSyncedDate;
        this.favoriteMealsCount = favoriteMealsCount;
        this.plannedMealsCount = plannedMealsCount;
    }


    public String getUid() {
        return uid;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String photoUrl) {
        this.imageUrl = photoUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public long getLastSyncedDate() {
        return lastSyncedDate;
    }

    public void setLastSyncedDate(long lastSyncedDate) {
        this.lastSyncedDate = lastSyncedDate;
    }

    public int getFavoriteMealsCount() {
        return favoriteMealsCount;
    }

    public void setFavoriteMealsCount(int favoriteMealsCount) {
        this.favoriteMealsCount = favoriteMealsCount;
    }

    public int getPlannedMealsCount() {
        return plannedMealsCount;
    }

    public void setPlannedMealsCount(int plannedMealsCount) {
        this.plannedMealsCount = plannedMealsCount;
    }
}
