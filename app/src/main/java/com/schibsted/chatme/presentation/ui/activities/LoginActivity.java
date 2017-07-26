package com.schibsted.chatme.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.schibsted.chatme.R;
import com.schibsted.chatme.presentation.ui.fragments.LoginFragment;

/**
 * Created by diego.galico
 */

public class LoginActivity extends BaseActivity implements LoginFragment.NavigationHandler {
    public static final String FLAG_COMMIT_FRAGMENT = "commitFragment";

    public static Intent getStartIntent(Context context, boolean commitFragment) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(FLAG_COMMIT_FRAGMENT, commitFragment);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        boolean commitFragment = intent.getBooleanExtra(FLAG_COMMIT_FRAGMENT, true);
        if (savedInstanceState == null && commitFragment) {
            addInfoFragment();
        }
    }

    public void addInfoFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, loginFragment)
                .commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_container;
    }

    @Override
    public void navigateToMessageActivity() {
        Intent myIntent = new Intent(this, MessageActivity.class);
        startActivity(myIntent);
        finish();
    }
}