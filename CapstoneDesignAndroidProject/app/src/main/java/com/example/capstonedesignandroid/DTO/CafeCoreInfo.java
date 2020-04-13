package com.example.capstonedesignandroid.DTO;

public class CafeCoreInfo {

    private int cafeId;
    private String cafeName;
    private int cafeCongestion;
    private int cafeTotalSeat;
    private double latitude;
    private double longitude;

    public CafeCoreInfo(int cafeId, String cafeName, int cafeCongestion, int cafeTotalSeat, double latitude, double longitude) {
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.cafeCongestion = cafeCongestion;
        this.cafeTotalSeat = cafeTotalSeat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCafeId() {
        return cafeId;
    }

    public void setCafeId(int cafeId) {
        this.cafeId = cafeId;
    }

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public int getCafeCongestion() {
        return cafeCongestion;
    }

    public void setCafeCongestion(int cafeCongestion) {
        this.cafeCongestion = cafeCongestion;
    }

    public int getCafeTotalSeat() {
        return cafeTotalSeat;
    }

    public void setCafeTotalSeat(int cafeTotalSeat) {
        this.cafeTotalSeat = cafeTotalSeat;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
