package com.example.capstonedesignandroid;


import com.example.capstonedesignandroid.DTO.Dummy;
import com.example.capstonedesignandroid.DTO.DummyCafeCoreInfo;
import com.example.capstonedesignandroid.DTO.DummyCurrentReservationBuildingFloor;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyLectureroomInfo;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationDetailGuard;
import com.example.capstonedesignandroid.DTO.DummyReservationId;
import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import com.example.capstonedesignandroid.DTO.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FirebaseService {

    //유저id에 FCM token을 저장한다. 주기적으로 token이 초기화되기 때문에 로그인마다 업데이트를 해준다.
    @POST("/")
    @FormUrlEncoded
    Call<DummyResponse> postUserToken(@Field("userId") String userId, @Field("fcmToken") String token);
    //입력: {userId: 1, fcmToken: fnadjsnvakjsdnjlavlkjvnl}
    //출력: {response: success}

    //아래는 테스트 용
    @POST("/study")
    @FormUrlEncoded
    Call<List<Dummy>> listDummies(@Field("id") String position, @Query("position") String position2);
    //Dummy3의 list형을 POST할 수 있도록 한다.
    //서버에서 req의 body id라는 field에(req.body.id) position이 들어갈 수 있도록 한다.
    //annotation이 붙은 Field나 Query는 server에서 사용하는 key값(변수)이고 오른쪽의 String 변수는 안드로이드에서 사용하는 변수다.
    //Post형은 server에서도 json형 데이터를 쉽게 확인하기 힘들다.

    @GET("/study/{position}")
    Call<List<Dummy>> listDummies2(@Path("position") String position);
    //Dummy3의 list형을 Get할 수 있도록 한다.
    //서버에서 req의 position이라는 path에(req.params.position) position이 들어갈 수 있도록 한다.
    //Get형은 주소에서 server에서도 json형 데이터를 확인할 수 있다.

}
