package com.schibsted.chatme.domain.interactors.impl;

import com.schibsted.chatme.data.cache.DiskCache;
import com.schibsted.chatme.domain.executors.SchedulerProvider;
import com.schibsted.chatme.domain.interactors.LoginInteractor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;

/**
 * Created by diego.galico
 */

public class LoginInteractorImpl extends LoginInteractor {

    private DiskCache mDiskCache;
    private Boolean mMemoryCache;
    private ReplaySubject<Boolean> mObserverEntity;

    @Inject
    public LoginInteractorImpl(SchedulerProvider schedulerProvider, DiskCache diskCache) {
        super(schedulerProvider);
        mDiskCache = diskCache;
    }

    @Override
    public Observable<Boolean> validateUsername(String username) {
        if (getSubscription() == null || getSubscription().isUnsubscribed()) {
            mObserverEntity = ReplaySubject.create();
            Subscription subscription = Observable.concat(memory(), disk(username))
                    .first(entity -> entity != null)
                    .compose(getSchedulerProvider().applySchedulers())
                    .subscribe(mObserverEntity);
            setSubscription(subscription);
        }
        return mObserverEntity.asObservable();
    }

    protected Observable<Boolean> disk(String username) {
        return mDiskCache.isUsernameValid(username)
                .doOnNext(entity -> mMemoryCache = entity);
    }

    protected Observable<Boolean> memory() {
        return Observable.just(mMemoryCache);
    }


    public boolean isLoggedUser() {
        return mDiskCache.isLoggedIn();
    }
}
