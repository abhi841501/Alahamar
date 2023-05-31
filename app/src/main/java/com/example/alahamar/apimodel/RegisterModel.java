package com.example.alahamar.apimodel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterModel {
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("success")
    @Expose
    public String success;

    public String getMsg() {
        return msg;
    }

    public String getSuccess() {
        return success;
    }

    }

