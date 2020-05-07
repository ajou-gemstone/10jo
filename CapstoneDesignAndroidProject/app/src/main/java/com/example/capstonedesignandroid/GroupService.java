package com.example.capstonedesignandroid;

import com.example.capstonedesignandroid.DTO.Group;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupService {

    @POST("/study")
    @FormUrlEncoded
    Call<List<Group>> listDummies(@Field("id") String position, @Field("pw") String position1);

    @GET("/study/{groupId}")
    Call<List<Group>> getStudyGroup(@Query("groupId") String groupId);

    @GET("/study/list")
    Call<List<Group>> getStudyList();

    @POST("/study/create")
    @FormUrlEncoded
    Call<List<Group>> postStudy(@Field("category") int category, @Field("title") String title, @Field("textBody") String textBody, @Field("tagName") String[] tagName, @Field("studyGroupNumTot") int studyGroupNumTot);


}
