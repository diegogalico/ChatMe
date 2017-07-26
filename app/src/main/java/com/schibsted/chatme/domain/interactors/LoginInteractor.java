package com.schibsted.chatme.domain.interactors;

import com.schibsted.chatme.domain.executors.SchedulerProvider;
import com.schibsted.chatme.domain.interactors.base.BaseInteractor;

import rx.Observable;

/**
 * Created by diego.galico
 */

public abstract class LoginInteractor<T> extends BaseInteractor {

    protected LoginInteractor(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public abstract Observable<T> validateUsername(String username);

    public abstract boolean isLoggedUser();

}
