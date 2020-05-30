package com.example.capstonedesignandroid.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DummySignUp {

    @SerializedName("userId")
    private String userId;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("studentNumber")
    private String studentNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("lecture")
    private ArrayList<String> lecture;
    @SerializedName("lectureCode")
    private ArrayList<String> lectureCode;

    public DummySignUp(String userId, String password, String name, String studentNumber, String email, ArrayList<String> lecture, ArrayList<String> lectureCode) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.studentNumber = studentNumber;
        this.email = email;
        this.lecture = lecture;
        this.lectureCode = lectureCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLecture(ArrayList<String> lecture) {
        this.lecture = lecture;
    }

    public void setLectureCode(ArrayList<String> lectureCode) {
        this.lectureCode = lectureCode;
    }

}
