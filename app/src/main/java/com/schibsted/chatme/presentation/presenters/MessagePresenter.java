package com.schibsted.chatme.presentation.presenters;

import com.schibsted.chatme.data.MessageEntity;
import com.schibsted.chatme.presentation.presenters.base.BasePresenter;
import com.schibsted.chatme.presentation.ui.BaseView;

import java.util.List;

/**
 * Created by diego.galico
 */

public class MessagePresenter<T extends BaseView> implements BasePresenter<T>{

    private T mMessageView;

    @Override
    public void attachView(T mvpView) {
        mMessageView = mvpView;
    }

    @Override
    public void detachView() {
        mMessageView = null;
    }

    public T getMessageView() {
        return mMessageView;
    }

    public interface MessageView extends BaseView {

        void showMessages(List<MessageEntity> listMessages);

    }
}
