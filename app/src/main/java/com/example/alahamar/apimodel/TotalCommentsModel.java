package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TotalCommentsModel {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("msg")
    @Expose
    public String msg;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TotalComments> getData() {
        return data;
    }

    public void setData(List<TotalComments> data) {
        this.data = data;
    }

    @SerializedName("data")
    @Expose
    public List<TotalComments> data;
}
