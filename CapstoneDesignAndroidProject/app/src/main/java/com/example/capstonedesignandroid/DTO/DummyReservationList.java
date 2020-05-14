package com.example.capstonedesignandroid.DTO;

public class DummyReservationList {

    String reservationId;
    String date;
    String day;
    String startTime;
    String lastTime;
    String lectureRoom;
    float timePriority;

    public DummyReservationList(String reservationId, String reservationDate, String day, String startTime, String listTime, String lectureRoom) {
        this.reservationId = reservationId;
        this.date = reservationDate;
        this.day = day;
        this.startTime = startTime;
        this.lastTime = listTime;
        this.lectureRoom = lectureRoom;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getLectureRoom() {
        return lectureRoom;
    }

    public float getTimePriority() {
        return timePriority;
    }

    public void setTimePriority(float timePriority) {
        this.timePriority = timePriority;
    }
}
