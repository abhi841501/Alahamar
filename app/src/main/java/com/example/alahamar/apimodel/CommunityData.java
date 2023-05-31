package com.example.alahamar.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommunityData {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image")
    @Expose
    public String image2;
    @SerializedName("topic_description")
    @Expose
    public String topicDescription;
    @SerializedName("headdings")
    @Expose
    public String headdings;
    @SerializedName("date")
    @Expose
    public String date1;
    @SerializedName("likes")
    @Expose
    public Integer likes;
    @SerializedName("comment")
    @Expose
    public Integer comment;
    @SerializedName("is_liked")
    @Expose
    public Integer isLiked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage(String image) {
        this.image2 = image;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getHeaddings() {
        return headdings;
    }

    public void setHeaddings(String headdings) {
        this.headdings = headdings;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate(String date) {
        this.date1 = date1;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Integer isLiked) {
        this.isLiked = isLiked;
    }
}
