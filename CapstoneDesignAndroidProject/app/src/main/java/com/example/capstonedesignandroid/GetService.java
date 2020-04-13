package com.example.capstonedesignandroid;

import com.example.capstonedesignandroid.DTO.Dummy;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetService {
    @GET("/test/{position}")
    Call<List<Dummy>> listDummies(@Path("position") String position);
}

