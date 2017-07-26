package com.schibsted.chatme.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schibsted.chatme.R;
import com.schibsted.chatme.data.exceptions.InvalidLoginException;
import com.schibsted.chatme.presentation.app.ChatmeApplication;
import com.schibsted.chatme.presentation.presenters.LoginPresenter;
import com.schibsted.chatme.presentation.presenters.impl.LoginPresenterImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by diego.galico
 */

public class LoginFragment extends BaseFragment implements LoginPresenter.LoginView {

    public interface NavigationHandler {
        void navigateToMessageActivity();
    }

    private NavigationHandler mNavigationHandler;
    private Unbinder mUnbinder;

    @Inject
    LoginPresenterImpl mLoginPresenter;

    @BindView(R.id.progress_bar_login)
    View mProgressBar;

    @BindView(R.id.input_layout_username)
    TextInputLayout mUsernameLayout;

    @BindView(R.id.edit_text_username)
    TextInputEditText mUsernameEditText;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
    }

    private void initializeDependencyInjector() {
        ChatmeApplication app = (ChatmeApplication) getActivity().getApplication();
        app.getApplicationComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mNavigationHandler = (NavigationHandler) getActivity();
        } catch (ClassCastException ex) {
            throw new ClassCastException(getActivity().toString() + " must implement navigateToMessageActivity");
        }
        if(mLoginPresenter.isLoggedUser()){
            startMessageActivity();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoginPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mLoginPresenter.detachView();
    }

    @OnClick(R.id.button_login)
    public void loginClick() {
        mLoginPresenter.setUsername(mUsernameEditText.getText().toString());
        mLoginPresenter.validateUsername();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Object message) {
        hideProgress();
        mUsernameLayout.setError(((InvalidLoginException) message).getMessage());
    }

    @Override
    public void startMessageActivity() {
        mNavigationHandler.navigateToMessageActivity();
    }

}
