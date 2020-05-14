package com.example.capstonedesignandroid.DTO;

public class DummyCurrentReservationBuildingFloor {

    String lectureRoomId;
    String lectureRoom;
    String reservationId;
    String startTime;
    String lastTime;
    String reservationType;
    String[] userId;

    public DummyCurrentReservationBuildingFloor(String lectureRoomId, String lectureRoom, String reservationId, String startTime, String lastTime, String reservationType, String[] userId) {
        this.lectureRoomId = lectureRoomId;
        this.lectureRoom = lectureRoom;
        this.reservationId = reservationId;
        this.startTime = startTime;
        this.lastTime = lastTime;
        this.reservationType = reservationType;
        this.userId = userId;
    }

    public String getLectureRoomId() {
        return lectureRoomId;
    }

    public String getLectureRoom() {
        return lectureRoom;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getReservationType() {
        return reservationType;
    }

    public String[] getUserId() {
        return userId;
    }
}
