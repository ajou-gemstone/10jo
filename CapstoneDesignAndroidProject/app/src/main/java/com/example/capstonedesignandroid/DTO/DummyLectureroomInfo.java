package com.example.capstonedesignandroid.DTO;

public class DummyLectureroomInfo {
    String capacity;

    public DummyLectureroomInfo(String capacity) {
        this.capacity = capacity;
    }

    public String getLectureroom() {
        return capacity;
    }

    public void setLectureroom(String capacity) {
        this.capacity = capacity;
    }
}
