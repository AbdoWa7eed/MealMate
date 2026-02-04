package com.iti.mealmate.core.network;

public class NoConnectivityException extends RuntimeException {

    public NoConnectivityException() {
        super("No internet connection available");
    }
}


