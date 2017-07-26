package com.schibsted.chatme.presentation.ui;

/**
 * Created by diego.galico
 */

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError(Object message);

}