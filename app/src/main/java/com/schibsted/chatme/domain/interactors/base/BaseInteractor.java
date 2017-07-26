package com.schibsted.chatme.domain.interactors.base;

import com.schibsted.chatme.domain.executors.SchedulerProvider;

import rx.Subscription;

/**
 * Created by diego.galico
 */

public abstract class BaseInteractor <T> {

    private SchedulerProvider mSchedulerProvider;

    private Subscription mSubscription;

    protected BaseInteractor(SchedulerProvider schedulerProvider) {
        mSchedulerProvider = schedulerProvider;
    }

    protected Subscription getSubscription(){
        return mSubscription;
    }

    protected void setSubscription(Subscription subscription){
        mSubscription = subscription;
    }

    protected SchedulerProvider getSchedulerProvider(){
        return mSchedulerProvider;
    }

    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
