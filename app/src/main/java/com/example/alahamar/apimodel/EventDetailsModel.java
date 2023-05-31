package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDetailsModel {
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("data")
    @Expose
    public List<EventData> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<EventData> getData() {
        return data;
    }

    public void setData(List<EventData> data) {
        this.data = data;
    }
}
