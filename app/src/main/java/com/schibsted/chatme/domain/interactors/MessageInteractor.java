package com.schibsted.chatme.domain.interactors;

import com.schibsted.chatme.domain.executors.SchedulerProvider;
import com.schibsted.chatme.domain.interactors.base.BaseInteractor;

import rx.Observable;

/**
 * Created by diego.galico
 */

public abstract class MessageInteractor<T> extends BaseInteractor {

    protected MessageInteractor(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public abstract Observable<T> getMessages();

    public abstract String getLoggedUser();

    public abstract void removeLoggedUser();
}
