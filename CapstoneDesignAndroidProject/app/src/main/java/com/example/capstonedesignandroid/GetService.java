package com.example.capstonedesignandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetService {
    @POST("/study")
    @FormUrlEncoded
    Call<List<Dummy3>> listDummies(@Field("id") String position, @Field("pw") String position1);

    @GET("/study/list")
    Call<List<Dummy3>> listDummies();
}
