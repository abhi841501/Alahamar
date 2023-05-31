package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunityDetailsModel {

    @SerializedName("data")
    @Expose
    public List<CommunityData> data;
    @SerializedName("success")
    @Expose
    public String success;

    public List<CommunityData> getData() {
        return data;
    }

    public void setData(List<CommunityData> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
