package com.example.capstonedesignandroid;

import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChattingService {
    @POST("chat/post")
    @FormUrlEncoded
    Call<DummyResponse> postChat(@Field("groupId") String groupId, @Field("userId") String userId, @Field("message") String message);
    //출력 : success of fail

    @GET("chat/get")
    Call<List<User>> getChat(@Query("groupId") String groupId);
    //출력 : 리스트 { leader:0 or 1, id : 3등등, message:"어쩌구", name:"주피터"}
}
