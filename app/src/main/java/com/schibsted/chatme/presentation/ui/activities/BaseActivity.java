package com.schibsted.chatme.presentation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by diego.galico
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();

}