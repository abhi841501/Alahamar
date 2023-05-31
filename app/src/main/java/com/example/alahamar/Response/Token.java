
package com.example.alahamar.Response;


import com.google.gson.annotations.SerializedName;



public class Token {

    @SerializedName("access")
    private String mAccess;
    @SerializedName("refresh")
    private String mRefresh;

    public String getAccess() {
        return mAccess;
    }

    public void setAccess(String access) {
        mAccess = access;
    }

    public String getRefresh() {
        return mRefresh;
    }

    public void setRefresh(String refresh) {
        mRefresh = refresh;
    }

}
