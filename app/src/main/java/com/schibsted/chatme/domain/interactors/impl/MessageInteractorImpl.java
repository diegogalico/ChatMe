package com.schibsted.chatme.domain.interactors.impl;

import com.schibsted.chatme.data.MessageWrapperEntity;
import com.schibsted.chatme.data.cache.DiskCache;
import com.schibsted.chatme.data.net.RestData;
import com.schibsted.chatme.domain.executors.SchedulerProvider;
import com.schibsted.chatme.domain.interactors.MessageInteractor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;

/**
 * Created by diego.galico
 */

public class MessageInteractorImpl extends MessageInteractor {

    private RestData mRestData;
    private DiskCache mDiskCache;
    private MessageWrapperEntity mMemoryCache;
    private ReplaySubject<MessageWrapperEntity> mObserverEntity;

    @Inject
    public MessageInteractorImpl(SchedulerProvider schedulerProvider, RestData restData, DiskCache diskCache) {
        super(schedulerProvider);
        mRestData = restData;
        mDiskCache = diskCache;
    }

    @Override
    public Observable<MessageWrapperEntity> getMessages() {
        if (getSubscription() == null || getSubscription().isUnsubscribed()) {
            mObserverEntity = ReplaySubject.create();
            Subscription subscription = Observable.concat(memory(), disk(), network())
                    .first(entity -> entity != null)
                    .compose(getSchedulerProvider().applySchedulers())
                    .subscribe(mObserverEntity);
            setSubscription(subscription);
        }

        return mObserverEntity.asObservable();
    }

    @Override
    public String getLoggedUser() {
        return mDiskCache.getLoggedUser();
    }

    @Override
    public void removeLoggedUser(){
        mDiskCache.removeLoggedUser();
    }

    protected Observable<MessageWrapperEntity> network() {
        return mRestData.getMessages()
                .doOnNext(entity -> mMemoryCache = entity)
                .flatMap(entity -> mDiskCache.saveMessageWrapper(entity).map(__ -> entity));
    }

    protected Observable<MessageWrapperEntity> disk() {
        return mDiskCache.getMessageWrapper()
                .doOnNext(entity -> mMemoryCache = entity);
    }

    protected Observable<MessageWrapperEntity> memory() {
        return Observable.just(mMemoryCache);
    }
}
