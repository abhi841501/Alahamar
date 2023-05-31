package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModelData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("user_receiver")
    @Expose
    public Integer userReceiver;
    @SerializedName("notification_message")
    @Expose
    public String notificationMessage;
    @SerializedName("notification_headding")
    @Expose
    public String notificationHeadding;
    @SerializedName("recieved_date")
    @Expose
    public String recievedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(Integer userReceiver) {
        this.userReceiver = userReceiver;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNotificationHeadding() {
        return notificationHeadding;
    }

    public void setNotificationHeadding(String notificationHeadding) {
        this.notificationHeadding = notificationHeadding;
    }

    public String getRecievedDate() {
        return recievedDate;
    }

    public void setRecievedDate(String recievedDate) {
        this.recievedDate = recievedDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    @SerializedName("read")
    @Expose
    public Boolean read;


    }

