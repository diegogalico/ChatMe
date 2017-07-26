package com.schibsted.chatme.data.net;

import com.schibsted.chatme.data.MessageWrapperEntity;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by diego.galico
 */

public class RestData {

    ApiInterface mApiInterface;

    @Inject
    public RestData(ApiInterface apiInterface) {
        mApiInterface = apiInterface;
    }

    public Observable<MessageWrapperEntity> getMessages() {
        Observable<MessageWrapperEntity> messages = mApiInterface.getMessages();
        return messages;
    }

}
