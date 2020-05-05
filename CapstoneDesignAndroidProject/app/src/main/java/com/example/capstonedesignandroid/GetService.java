package com.example.capstonedesignandroid;

import com.example.capstonedesignandroid.DTO.Group;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyLectureroomInfo;
import com.example.capstonedesignandroid.DTO.DummyReservationId;
import com.example.capstonedesignandroid.DTO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetService {

    @POST("/login")
    Call<List<User>> postUser(@Field("id") String id, @Field("password") String password);

//    Call<List<Group>> listDummies(@Field("id") String position, @Query("position") String position2);
    //Dummy3의 list형을 POST할 수 있도록 한다.
    //서버에서 req의 body id라는 field에(req.body.id) position이 들어갈 수 있도록 한다.
    //annotation이 붙은 Field나 Query는 server에서 사용하는 key값(변수)이고 오른쪽의 String 변수는 안드로이드에서 사용하는 변수다.
    //Post형은 server에서도 json형 데이터를 쉽게 확인하기 힘들다.

    @GET("/study/{position}")
    Call<List<Group>> listDummies2(@Path("position") String position);
    //Dummy3의 list형을 Get할 수 있도록 한다.
    //서버에서 req의 position이라는 path에(req.params.position) position이 들어갈 수 있도록 한다.
    //Get형은 주소에서 server에서도 json형 데이터를 확인할 수 있다.

    //강의실 예약 필터링
    @GET("/reservation/list")
    Call<List<DummyLectureRoomReservationState>> getReservationList(@Query("date") String date, @Query("building") String[] building,
                                                                    @Query("startTime") int startTime, @Query("lastTime") int lastTime);
    //입력: 날짜(하나), 건물(리스트), 시작시간(하나), 종료시간(하나), //강의실 예약 인원 수(하나)
    //입력: {date: "YYYY-M-D", building: "성호관 율곡관 연암관" startTime: "0" lastTime: "3"}
    //출력: {lectureroom: "성101", stateList: "R 0 0 0 1 L"}
    //출력: {lectureroom: "성103", stateList: "R A A A L L"}

    //강의실 정보 가져오기
    @GET("/")
    Call<DummyLectureroomInfo> getLectureroomInfo(@Query("lectureroom") String lectureroom);
    //입력: {lectureroom: 성101}
    //출력: {capacity: 50}

    //강의실 예약 저장하기
    @POST("/")
    Call<DummyReservationId> postReservation(@Query("date") String date, @Query("lectureroom") String lectureroom,
                                             @Query("startTime") int startTime, @Query("lastTime") int lastTime,
                                             @Query("userid") String userid);
    //입력: 날짜(하나), 강의실(하나), 시작시간(하나), 종료시간(하나), 본인id(하나)
    //입력: {date: "YYYY-M-D" lectureRoom: "성101" startTime: "9:00" lastTime: "10:00", userid: akdsnmkq}
    //출력: {예약내역id: qninia} - 나중에 추가정보를 입력할 때 이 예약내역 id를 이용한다.




}
