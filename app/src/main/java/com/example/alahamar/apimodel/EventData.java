package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("headding")
    @Expose
    public String headding;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("gallery")
    @Expose
    public List<EventGallery> eventGallery;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeadding() {
        return headding;
    }

    public void setHeadding(String headding) {
        this.headding = headding;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<EventGallery> getGallery() {
        return eventGallery;
    }

    public void setGallery(List<EventGallery> eventGallery) {
        this.eventGallery = eventGallery;
    }
}
