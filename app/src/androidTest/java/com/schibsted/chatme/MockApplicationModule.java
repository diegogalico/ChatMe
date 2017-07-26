package com.schibsted.chatme;

import android.content.SharedPreferences;

import com.schibsted.chatme.data.cache.DiskCache;
import com.schibsted.chatme.domain.executors.SchedulerProvider;
import com.schibsted.chatme.presentation.app.ChatmeApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.mock;

/**
 * Created by diego.galico
 */

@Module
public class MockApplicationModule {
    private final SharedPreferences mockSharedPreferences = mock(SharedPreferences.class);
    private final ChatmeApplication mockApp = mock(ChatmeApplication.class);
    private final DiskCache mockDiskCache = mock(DiskCache.class);
    private final TestScheduler testScheduler = new TestScheduler();

    @Provides
    @Singleton
    ChatmeApplication provideApplication() {
        return mockApp;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return mockSharedPreferences;
    }

    @Provides @Singleton SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider() {
            @Override public <T> Observable.Transformer<T, T> applySchedulers() {
                return tObservable -> tObservable
                        .subscribeOn(testScheduler)
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Provides TestScheduler provideTestScheduler() {
        return testScheduler;
    }


    @Provides DiskCache provideDiskCache() {
        return mockDiskCache;
    }



}
