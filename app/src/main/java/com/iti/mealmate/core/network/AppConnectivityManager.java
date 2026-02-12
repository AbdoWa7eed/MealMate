package com.iti.mealmate.core.network;

import io.reactivex.rxjava3.core.Observable;

public interface AppConnectivityManager {

    boolean isConnected();

    Observable<Boolean> connectivityStream();

    void unregister();
}

