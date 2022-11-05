package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class marker implements Serializable {

    public marker()
    {}

    @SerializedName("markerCategory")
    private String markerCategory;

    @SerializedName("isDanger")
    private int isDanger;

    @SerializedName("markerInform")
    private String markerInform;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("markerLongitude")
    private Double markerLongitude;

    @SerializedName("markerLatitude")
    private Double markerLatitude;

    @SerializedName("postingDay")
    private String postingDay;

    @SerializedName("posterNickName")
    private String posterNickName;
    @SerializedName("markerId")
    private int markerId;

    public marker(int isDanger,String markerInform,String imageUrl,Double markerLatitude,Double markerLongitude,String posterNickName,String markerCategory,int markerId)
    {

        this.isDanger=isDanger;
        this.markerInform=markerInform;
        this.imageUrl=imageUrl;
        this.markerLatitude=markerLatitude;
        this.markerLongitude=markerLongitude;
        this.posterNickName=posterNickName;
        this.markerCategory=markerCategory;
        this.markerId = markerId;
    }
    public marker(int isDanger,String markerInform,String imageUrl,Double markerLatitude,Double markerLongitude,String posterNickName,String markerCategory)
    {

        this.isDanger=isDanger;
        this.markerInform=markerInform;
        this.imageUrl=imageUrl;
        this.markerLatitude=markerLatitude;
        this.markerLongitude=markerLongitude;
        this.posterNickName=posterNickName;
        this.markerCategory=markerCategory;

    }
    public int getMarkerId(){return this.markerId;}

    public String getMarkerCategory() {
        return this.markerCategory;
    }

    public String getMarkerInform() {
        return this.markerInform;
    }

    public Integer getIsDanger() {
        return this.isDanger;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public double getMarkerLongitude() {
        return this.markerLongitude;
    }

    public double getMarkerLatitude() {
        return this.markerLatitude;
    }

    public String getPostingDay() {
        return this.postingDay;
    }

    public String getPosterNickName() {
        return this.posterNickName;
    }

    public void setMarkerId(final int markerId) {
        this.markerId = markerId;
    }

    public void setMarkerCategory(final String markerCategory) {
        this.markerCategory = markerCategory;
    }

    public void setMarkerInform(final String markerInform) {
        this.markerInform = markerInform;
    }

    public void setIsDanger(final Integer isDanger) {
        this.isDanger = isDanger;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMarkerLongitude(final double markerLongitude) {
        this.markerLongitude = markerLongitude;
    }

    public void setMarkerLatitude(final double markerLatitude) {
        this.markerLatitude = markerLatitude;
    }

    public void setPostingDay(final String postingDay) {
        this.postingDay = postingDay;
    }

    public void setPosterNickName(final String posterNickName) {
        this.posterNickName = posterNickName;
    }



}
