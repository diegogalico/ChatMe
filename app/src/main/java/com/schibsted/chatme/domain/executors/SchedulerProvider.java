package com.schibsted.chatme.domain.executors;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by diego.galico
 */

public interface SchedulerProvider {
    <T> Observable.Transformer<T, T> applySchedulers();

    SchedulerProvider DEFAULT = new SchedulerProvider() {
        @Override public <T> Observable.Transformer<T, T> applySchedulers() {
            return observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };
}
