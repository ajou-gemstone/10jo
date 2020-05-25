package com.example.capstonedesignandroid.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DummyTile2 {

    @SerializedName("userId")
    String userId;
    @SerializedName("info")
    List<DummyTile> info;

    public DummyTile2(String userId, List<DummyTile> info) {
        this.userId = userId;
        this.info = info;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DummyTile> getInfo() {
        return info;
    }

    public void setInfo(List<DummyTile> info) {
        this.info = info;
    }

}
