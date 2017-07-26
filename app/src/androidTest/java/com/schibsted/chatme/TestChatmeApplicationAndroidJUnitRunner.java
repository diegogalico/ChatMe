package com.schibsted.chatme;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by diego.galico
 */

public class TestChatmeApplicationAndroidJUnitRunner extends AndroidJUnitRunner {
    @Override public Application newApplication(@NonNull ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestChatmeApplication.class.getName(), context);
    }
}

