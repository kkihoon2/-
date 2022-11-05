package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JwtInform implements Serializable {


    @SerializedName("grantType")
    private String grantType;

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("tokenExpiresIn")
    private long tokenExpiresIn;
    public JwtInform(){}



    public JwtInform(String grantType, String accessToken, long  tokenExpiresIn)
    {

        this.grantType=grantType;
        this.accessToken=accessToken;
        this.tokenExpiresIn=tokenExpiresIn;

    }


    public String getGrantType() {
        return this.grantType;
    }

    public String getAccessToken() {
        return this.accessToken;
    }


    public long  getTokenExpiresIn() { return this.tokenExpiresIn;}


    public void setGrantType(final String grantType) {
        this.grantType = grantType;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenExpiresIn(final long  tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }





}
