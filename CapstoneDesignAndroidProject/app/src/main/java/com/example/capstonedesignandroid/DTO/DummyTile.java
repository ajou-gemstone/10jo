package com.example.capstonedesignandroid.DTO;

import com.google.gson.annotations.SerializedName;

public class DummyTile {
    @SerializedName("contents")
    String contents;
    @SerializedName("time")
    String time;

    public DummyTile(String contents, String time) {
        this.contents = contents;
        this.time = time;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
