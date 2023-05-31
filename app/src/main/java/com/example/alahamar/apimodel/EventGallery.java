package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventGallery {

    @SerializedName("image")
    @Expose
    public String image;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
