package com.schibsted.chatme.presentation.presenters;

import com.schibsted.chatme.presentation.presenters.base.BasePresenter;
import com.schibsted.chatme.presentation.ui.BaseView;

/**
 * Created by diego.galico
 */

public class LoginPresenter<T extends BaseView> implements BasePresenter<T>{

    private T mLoginView;

    @Override
    public void attachView(T mvpView) {
        mLoginView = mvpView;
    }

    @Override
    public void detachView() {
        mLoginView = null;
    }

    public T getLoginView() {
        return mLoginView;
    }

    public interface LoginView extends BaseView {

        void startMessageActivity();
    }
}
