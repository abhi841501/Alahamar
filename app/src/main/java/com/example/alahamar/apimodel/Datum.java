
package com.example.alahamar.apimodel;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Datum {

    @SerializedName("designation")
    private String mDesignation;
    @SerializedName("image")
    private String mImage;
    @SerializedName("name")
    private String mName;

    public String getDesignation() {
        return mDesignation;
    }

    public void setDesignation(String designation) {
        mDesignation = designation;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
