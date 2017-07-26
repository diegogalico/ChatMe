package com.schibsted.chatme.presentation.presenters.impl;

import com.schibsted.chatme.domain.interactors.impl.MessageInteractorImpl;
import com.schibsted.chatme.presentation.presenters.MessagePresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by diego.galico
 */

public class MessagePresenterImpl extends MessagePresenter<MessagePresenter.MessageView> {

    private Subscription mSubscription = Subscriptions.empty();
    private MessageInteractorImpl mMessageInteractor;

    @Inject
    public MessagePresenterImpl(MessageInteractorImpl getMessageInteractor) {
        mMessageInteractor = getMessageInteractor;
    }

    @Override
    public void attachView(MessagePresenter.MessageView messageView) {
        super.attachView(messageView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void getMessages() {
        getMessageView().showProgress();
        mSubscription = mMessageInteractor.getMessages().subscribe(
                loginEntity -> getMessageView().showMessages(loginEntity.getListMessages()),
                error -> getMessageView().hideProgress(),
                getMessageView()::hideProgress);
    }

    public String getLoggedUser(){
        return mMessageInteractor.getLoggedUser();
    }

    public void removeLoggedUser(){
        mMessageInteractor.removeLoggedUser();
    }
}
