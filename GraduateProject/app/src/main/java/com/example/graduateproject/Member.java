package com.example.graduateproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Member implements Serializable {


    @SerializedName("memberId")
    private String memberId;

    @SerializedName("password")
    private String password;

    @SerializedName("nickname")
    private String nickname;



    public Member(String memberId, String password, String nickname)
    {

        this.memberId=memberId;
        this.password=password;
        this.nickname=nickname;

    }


    public String getMemberId() {
        return this.memberId;
    }

    public String getPassword() {
        return this.password;
    }


    public String getNickname() { return this.nickname;}


    public void setMemberId(final String memberId) {
        this.memberId = memberId;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setImageUrl(final String nickname) {
        this.nickname = nickname;
    }





}
