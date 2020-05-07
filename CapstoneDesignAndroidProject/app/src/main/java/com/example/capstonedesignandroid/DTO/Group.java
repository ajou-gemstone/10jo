package com.example.capstonedesignandroid.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Group {

    private Integer id;
    private String category;
    private String title;
    private String textBody;
    private Integer studyGroupNumTotal;
    private Integer studyGroupNumCurrent;
    private Integer imageUri;
    private List<TagName> tagName = null;
    private List<User> user = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public Integer getStudyGroupNumTotal() {
        return studyGroupNumTotal;
    }

    public void setStudyGroupNumTotal(Integer studyGroupNumTotal) {
        this.studyGroupNumTotal = studyGroupNumTotal;
    }

    public Integer getStudyGroupNumCurrent() {
        return studyGroupNumCurrent;
    }

    public void setStudyGroupNumCurrent(Integer studyGroupNumCurrent) {
        this.studyGroupNumCurrent = studyGroupNumCurrent;
    }

    public Integer getImageUri() {
        return imageUri;
    }

    public void setImageUri(Integer imageUri) {
        this.imageUri = imageUri;
    }

    public List<TagName> getTagName() {
        return tagName;
    }

    public void setTagName(List<TagName> tagName) {
        this.tagName = tagName;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}