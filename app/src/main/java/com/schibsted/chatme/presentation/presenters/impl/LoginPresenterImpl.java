package com.schibsted.chatme.presentation.presenters.impl;

import com.schibsted.chatme.domain.interactors.LoginInteractor;
import com.schibsted.chatme.domain.interactors.impl.LoginInteractorImpl;
import com.schibsted.chatme.presentation.presenters.LoginPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by diego.galico
 */

public class LoginPresenterImpl extends LoginPresenter<LoginPresenter.LoginView> {

    private Subscription mSubscription = Subscriptions.empty();
    private LoginInteractor mLoginInteractor;
    private String mUsername;

    @Inject
    public LoginPresenterImpl(LoginInteractorImpl getLoginInteractor) {
        mLoginInteractor = getLoginInteractor;
    }

    @Override
    public void attachView(LoginView loginView) {
        super.attachView(loginView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public void validateUsername() {
        getLoginView().showProgress();
        mSubscription = mLoginInteractor.validateUsername(mUsername).subscribe(
                loginEntity -> getLoginView().startMessageActivity(),
                error -> getLoginView().showError(error),
                getLoginView()::hideProgress);
    }

    public boolean isLoggedUser() {
        return mLoginInteractor.isLoggedUser();
    }
}
