package com.example.capstonedesignandroid.DTO;

public class LectureRoomReservationState {
    String lectureroom;
    String stateList;
    int priority;

    public LectureRoomReservationState(DummyLectureRoomReservationState dummyLectureRoomReservationState) {
        this.lectureroom = dummyLectureRoomReservationState.getLectureroom();
        this.stateList = dummyLectureRoomReservationState.getStateList();
        this.priority = 0;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
