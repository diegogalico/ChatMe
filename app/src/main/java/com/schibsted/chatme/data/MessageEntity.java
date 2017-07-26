package com.schibsted.chatme.data;

import com.google.gson.annotations.SerializedName;
import com.schibsted.chatme.presentation.ui.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by diego.galico
 */

public class MessageEntity implements Serializable {

    @SerializedName("username")
    private String username;
    @SerializedName("content")
    private String content;
    @SerializedName("userImage_url")
    private String imageUrl;
    @SerializedName("time")
    private String time;
    private boolean isMyMessage = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isMyMessage() {
        return isMyMessage;
    }

    public void setMyMessage(boolean myMessage) {
        isMyMessage = myMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (StringUtils.equalsWithNulls(username, (((MessageEntity) obj).getUsername())) &&
                StringUtils.equalsWithNulls(content, (((MessageEntity) obj).getContent())) &&
                StringUtils.equalsWithNulls(imageUrl, (((MessageEntity) obj).getImageUrl())) &&
                StringUtils.equalsWithNulls(time, (((MessageEntity) obj).getTime()))) {
            return true;
        } else {
            return false;
        }
    }
}
