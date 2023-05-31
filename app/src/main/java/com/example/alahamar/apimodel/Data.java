package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("id")
@Expose
public Integer id;
@SerializedName("profile")
@Expose
public String profile;
@SerializedName("national_id")
@Expose
public String nationalId;
@SerializedName("name")
@Expose
public String name;
@SerializedName("city")
@Expose
public String city;
@SerializedName("email")
@Expose
public String email;
@SerializedName("mobile")
@Expose
public String mobile;
@SerializedName("city_name")
@Expose
public String cityName;
@SerializedName("device_token")
@Expose
public String deviceToken;
@SerializedName("auth_token")
@Expose
public String authToken;
@SerializedName("status")
@Expose
public Integer status;
@SerializedName("updated_at")
@Expose
public String updatedAt;
@SerializedName("created_at")
@Expose
public String createdAt;

    public Integer getId() {
        return id;
    }

    public String getProfile() {
        return profile;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Integer getStatus() {
        return status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}