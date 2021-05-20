
package com.sngs.swayam.business.network.model.Notification;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NotificationBaseResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("notification")
    private List<Notification> mNotification;
    @SerializedName("success")
    private Long mSuccess;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<Notification> getNotification() {
        return mNotification;
    }

    public void setNotification(List<Notification> notification) {
        mNotification = notification;
    }

    public Long getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Long success) {
        mSuccess = success;
    }

}
