package com.example.capstonedesignandroid.DTO;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String userId;
    private String userPassword;
    private String email;
    private String studentNum;
    private Integer userType;
    private String name;
    private Integer score;
    private Integer leader;
    private String message;
    private ArrayList<String> lectureList = null;

    public User(String name, String studentNum) {
        this.name = name;
        this.studentNum = studentNum;
    }

    public String getId() {
        return id;
    } //primary key

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getLeader() {
        return leader;
    }

    public void setLeader(Integer leader) {
        this.leader = leader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getLecture() {
        return lectureList;
    }

    public void setLecture(ArrayList<String> lectureList) {
        this.lectureList = lectureList;
    }

}
