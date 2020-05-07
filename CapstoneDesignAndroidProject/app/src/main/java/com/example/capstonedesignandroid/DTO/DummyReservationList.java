package com.example.capstonedesignandroid.DTO;

public class DummyReservationList {

    String reservationId;
    String reservationDate;
    String day;
    String startTime;
    String listTime;
    String lectureRoom;

    public DummyReservationList(String reservationId, String reservationDate, String day, String startTime, String listTime, String lectureRoom) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.day = day;
        this.startTime = startTime;
        this.listTime = listTime;
        this.lectureRoom = lectureRoom;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getListTime() {
        return listTime;
    }

    public String getLectureRoom() {
        return lectureRoom;
    }
}
