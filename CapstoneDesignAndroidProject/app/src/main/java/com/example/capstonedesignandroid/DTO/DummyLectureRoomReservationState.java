package com.example.capstonedesignandroid.DTO;

public class DummyLectureRoomReservationState{
    String lectureroom;
    String stateList;

    public DummyLectureRoomReservationState(String lectureroom, String stateList) {
        this.lectureroom = lectureroom;
        this.stateList = stateList;
    }

    public String getLectureroom() {
        return lectureroom;
    }

    public void setLectureroom(String lectureroom) {
        this.lectureroom = lectureroom;
    }

    public String getStateList() {
        return stateList;
    }

    public void setStateList(String stateList) {
        this.stateList = stateList;
    }
}
