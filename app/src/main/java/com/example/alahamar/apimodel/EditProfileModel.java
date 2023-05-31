package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileModel {


    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public EditprofileData data;

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

    public EditprofileData getData() {
        return data;
    }

    public void setData(EditprofileData data) {
        this.data = data;
    }
}
