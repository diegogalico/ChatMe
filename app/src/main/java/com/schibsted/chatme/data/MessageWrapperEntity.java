package com.schibsted.chatme.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by diego.galico
 */

public class MessageWrapperEntity {

    @SerializedName("chats")
    private List<MessageEntity> listMessages;

    public List<MessageEntity> getListMessages() {
        return listMessages;
    }

    public void setListMessages(List<MessageEntity> listMessages) {
        this.listMessages = listMessages;
    }

    @Override
    public boolean equals(Object obj) {
        if(listMessages.get(0).equals(((MessageWrapperEntity)obj).getListMessages().get(0))){
            return true;
        }
        return super.equals(obj);
    }
}
