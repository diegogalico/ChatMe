package com.schibsted.chatme.presentation.presenters.base;

import com.schibsted.chatme.presentation.ui.BaseView;

/**
 * Created by diego.galico
 */

public interface BasePresenter <V extends BaseView>{

    void attachView(V mvpView);

    void detachView();
}