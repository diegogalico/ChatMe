package com.schibsted.chatme.presentation.app;

import android.app.Application;

import com.schibsted.chatme.presentation.app.dependencyinjection.components.ApplicationComponent;
import com.schibsted.chatme.presentation.app.dependencyinjection.components.DaggerApplicationComponent;
import com.schibsted.chatme.presentation.app.dependencyinjection.components.DaggerNetworkComponent;
import com.schibsted.chatme.presentation.app.dependencyinjection.components.NetworkComponent;
import com.schibsted.chatme.presentation.app.dependencyinjection.modules.ApplicationModule;
import com.schibsted.chatme.presentation.app.dependencyinjection.modules.NetworkModule;

/**
 * Created by diego.galico
 */

public class ChatmeApplication extends Application {

    private ApplicationComponent mAppComponent;
    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mNetworkComponent = DaggerNetworkComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(Constants.ENDPOINT))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

}
