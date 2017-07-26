package com.schibsted.chatme.data.net;

import com.schibsted.chatme.data.MessageWrapperEntity;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by diego.galico
 */

public interface ApiInterface {

    @GET("/v1/messages")
    Observable<MessageWrapperEntity> getMessages();
}
