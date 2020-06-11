package com.example.capstonedesignandroid.DTO;

import com.google.gson.annotations.SerializedName;

public class DummyStudentNameId {

    @SerializedName("userId")
    String userId;
    @SerializedName("name")
    String name;

    public DummyStudentNameId(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
