
package com.example.alahamar.Response;


import com.google.gson.annotations.SerializedName;



public class LoginModel {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("image")
    private String mImage;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("phone_number")
    private String mPhoneNumber;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("token")
    private Token mToken;
    @SerializedName("user_id")
    private Long mUserId;
    @SerializedName("username")
    private String mUsername;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getSuccess() {
        return mSuccess;
    }

    public void setSuccess(String success) {
        mSuccess = success;
    }

    public Token getToken() {
        return mToken;
    }

    public void setToken(Token token) {
        mToken = token;
    }

    public Long getUserId() {
        return mUserId;
    }

    public void setUserId(Long userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
