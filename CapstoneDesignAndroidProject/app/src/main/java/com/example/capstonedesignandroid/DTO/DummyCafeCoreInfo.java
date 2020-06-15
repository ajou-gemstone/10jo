package com.example.capstonedesignandroid.DTO;

public class DummyCafeCoreInfo {

    private int cafeId;
    private String name;
    private int congestion;
    private int cafeTotalSeat;
    private double latitude;
    private double longitude;
    private String address;
    private String cafeBody;
    private String updateTime;

    public DummyCafeCoreInfo(int cafeId, String name, int congestion, int cafeTotalSeat, double latitude, double longitude, String cafeBody) {
        this.cafeId = cafeId;
        this.name = name;
        this.congestion = congestion;
        this.cafeTotalSeat = cafeTotalSeat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cafeBody = cafeBody;
    }

    public int getCafeId() {
        return cafeId;
    }

    public String getName() {
        return name;
    }

    public int getCongestion() {
        return congestion;
    }

    public int getCafeTotalSeat() {
        return cafeTotalSeat;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCafeBody() {
        return cafeBody;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
