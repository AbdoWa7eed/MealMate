package com.iti.mealmate.core.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AppConnectivityManagerImpl implements AppConnectivityManager {

    private final ConnectivityManager connectivityManager;
    private final BehaviorSubject<Boolean> connectivitySubject = BehaviorSubject.create();
    private final ConnectivityManager.NetworkCallback networkCallback;

    private volatile boolean isConnected;

    public AppConnectivityManagerImpl(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.isConnected = calculateIsConnected();
        connectivitySubject.onNext(isConnected);

        this.networkCallback = createNetworkCallback();
        registerNetworkCallback();
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public Observable<Boolean> connectivityStream() {
        return connectivitySubject.hide().distinctUntilChanged();
    }

    private ConnectivityManager.NetworkCallback createNetworkCallback() {
        return new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                updateConnectionState(true);
            }

            @Override
            public void onLost(@NonNull Network network) {
                updateConnectionState(calculateIsConnected());
            }

            @Override
            public void onCapabilitiesChanged(@NonNull Network network,@NonNull NetworkCapabilities capabilities) {
                boolean hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                updateConnectionState(hasInternet);
            }
        };
    }

    private void registerNetworkCallback() {
        if (connectivityManager == null) {
            updateConnectionState(false);
            return;
        }

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }


    @Override
    public void unregister() {
        if (connectivityManager != null && networkCallback != null) {
            try {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            } catch (IllegalArgumentException ignored) {
            }
        }
        connectivitySubject.onComplete();
    }

    private boolean calculateIsConnected() {
        if (connectivityManager == null) {
            return false;
        }
        Network network = connectivityManager.getActiveNetwork();
        if (network == null) {
            return false;
        }
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
        return capabilities != null
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    private void updateConnectionState(boolean newState) {
        if (isConnected == newState) {
            return;
        }
        isConnected = newState;
        connectivitySubject.onNext(newState);
    }
}