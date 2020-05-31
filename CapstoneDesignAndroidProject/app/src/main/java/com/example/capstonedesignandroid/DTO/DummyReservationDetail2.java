package com.example.capstonedesignandroid.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DummyReservationDetail2 {

    @SerializedName("reservationId")
    String reservationId;
    @SerializedName("reservationIntent")
    String reservationIntent;
    @SerializedName("userClassofsNum")
    int userClassofsNum;
    @SerializedName("userClassofs")
    ArrayList<String> userClassofs;

    public DummyReservationDetail2(String reservationId, String reservationIntent, int userClassofsNum, ArrayList<String> userClassofs) {
        this.reservationId = reservationId;
        this.reservationIntent = reservationIntent;
        this.userClassofsNum = userClassofsNum;
        this.userClassofs = userClassofs;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationIntent() {
        return reservationIntent;
    }

    public void setReservationIntent(String reservationIntent) {
        this.reservationIntent = reservationIntent;
    }

    public int getUserClassofsNum() {
        return userClassofsNum;
    }

    public void setUserClassofsNum(int userClassofsNum) {
        this.userClassofsNum = userClassofsNum;
    }

    public ArrayList<String> getUserClassofs() {
        return userClassofs;
    }

    public void setUserClassofs(ArrayList<String> userClassofs) {
        this.userClassofs = userClassofs;
    }
}
