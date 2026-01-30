package com.iti.mealmate.ui.onboarding.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OnboardingPage implements Parcelable {
    private final int imageResId;
    private final int titleResId;
    private final int descriptionResId;

    public OnboardingPage(int imageResId, int titleResId, int descriptionResId) {
        this.imageResId = imageResId;
        this.titleResId = titleResId;
        this.descriptionResId = descriptionResId;
    }

    protected OnboardingPage(Parcel in) {
        imageResId = in.readInt();
        titleResId = in.readInt();
        descriptionResId = in.readInt();
    }

    public static final Creator<OnboardingPage> CREATOR = new Creator<OnboardingPage>() {
        @Override
        public OnboardingPage createFromParcel(Parcel in) {
            return new OnboardingPage(in);
        }

        @Override
        public OnboardingPage[] newArray(int size) {
            return new OnboardingPage[size];
        }
    };

    public int getImageResId() {
        return imageResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getDescriptionResId() {
        return descriptionResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResId);
        dest.writeInt(titleResId);
        dest.writeInt(descriptionResId);
    }
}