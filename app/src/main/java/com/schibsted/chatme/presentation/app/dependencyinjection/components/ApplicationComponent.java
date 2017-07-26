package com.schibsted.chatme.presentation.app.dependencyinjection.components;

import com.schibsted.chatme.presentation.app.dependencyinjection.modules.ApplicationModule;
import com.schibsted.chatme.presentation.ui.fragments.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by diego.galico
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(LoginFragment fragment);
}
