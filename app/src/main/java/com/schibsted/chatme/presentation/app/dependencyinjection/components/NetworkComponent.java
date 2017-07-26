package com.schibsted.chatme.presentation.app.dependencyinjection.components;

import com.schibsted.chatme.presentation.app.dependencyinjection.modules.ApplicationModule;
import com.schibsted.chatme.presentation.app.dependencyinjection.modules.NetworkModule;
import com.schibsted.chatme.presentation.ui.fragments.MessageFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by diego.galico
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject(MessageFragment fragment);
}
