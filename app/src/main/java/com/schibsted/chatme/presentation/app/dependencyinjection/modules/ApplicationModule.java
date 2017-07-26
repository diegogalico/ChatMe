package com.schibsted.chatme.presentation.app.dependencyinjection.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.schibsted.chatme.domain.executors.SchedulerProvider;
import com.schibsted.chatme.presentation.app.ChatmeApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by diego.galico
 */

@Module
public class ApplicationModule {

    private ChatmeApplication mApplication;

    public ApplicationModule(ChatmeApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    ChatmeApplication provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.DEFAULT;
    }

}
