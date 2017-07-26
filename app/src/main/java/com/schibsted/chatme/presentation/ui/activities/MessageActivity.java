package com.schibsted.chatme.presentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.schibsted.chatme.R;
import com.schibsted.chatme.presentation.ui.fragments.MessageFragment;

/**
 * Created by diego.galico
 */

public class MessageActivity extends BaseActivity implements MessageFragment.NavigationHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            addInfoFragment();
        }
    }

    public void addInfoFragment() {
        MessageFragment messageFragment = MessageFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, messageFragment)
                .commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_container;
    }

    @Override
    public void navigateToLoginActivity() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }

}
