package com.schibsted.chatme;

import com.schibsted.chatme.presentation.app.ChatmeApplication;
import com.schibsted.chatme.presentation.app.dependencyinjection.components.ApplicationComponent;

/**
 * Created by diego.galico
 */

public class TestChatmeApplication extends ChatmeApplication {
    @Override
    public ApplicationComponent getApplicationComponent() {
        return DaggerMockApplicationComponent.builder()
                .mockApplicationModule(new MockApplicationModule())
                .build();
    }
}


