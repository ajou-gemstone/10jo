package com.example.capstonedesignandroid;

import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.Group;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GroupService {

    @GET("/study")
    Call<Group> getStudyGroup(@Query("groupId") String groupId);
    //출력 : title, textBody, studyGroupNumTot, Cur, Tag리스트, user리스트(user 각각에는 usertype(모임장/원), name)

    @GET("/study/list")
    Call<List<Group>> getStudyList();

    @GET("/study/mylist")
    Call<List<Group>> getMyStudyList(@Query("userId") String userId);
    //출력 : userId가 가입하고 있는 모임 리스트 - id, tagName, totalnum, currentnum

    @POST("/study/create")
    @FormUrlEncoded
    Call<DummyResponse> createStudy(@Field("userId") String userId, @Field("category") String category, @Field("title") String title, @Field("textBody") String textBody, @Field("tagName") ArrayList<String> tagName, @Field("studyGroupNumTot") int studyGroupNumTot);
    //출력 : success or fail

    @POST("/study/register")
    @FormUrlEncoded
    Call<DummyResponse> registerStudy(@Field("groupId") String groupId, @Field("userId") String userId);
    //출력 : success or fail

    @POST("/study/edit")
    @FormUrlEncoded
    Call<DummyResponse> editStudy(@Field("groupId") String groupId, @Field("title") String title, @Field("textBody") String textBody, @Field("tagName") ArrayList<String> tagName, @Field("studyGroupNumTot") String studyGroupNumTot);
    //출력 : success or fail

}
