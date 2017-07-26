package com.schibsted.chatme;

import com.schibsted.chatme.data.cache.DiskCache;
import com.schibsted.chatme.presentation.app.dependencyinjection.components.ApplicationComponent;

import javax.inject.Singleton;

import dagger.Component;
import rx.schedulers.TestScheduler;

/**
 * Created by diego.galico
 */

@Singleton
@Component(modules = MockApplicationModule.class)
public interface MockApplicationComponent extends ApplicationComponent {
    TestScheduler getTestScheduler();
    DiskCache getDiskCache();
}

