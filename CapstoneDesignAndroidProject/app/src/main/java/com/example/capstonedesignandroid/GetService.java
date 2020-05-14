package com.example.capstonedesignandroid;


import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyLectureroomInfo;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationId;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.DTO.DummyResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetService {

    @POST("user/signup")
    @FormUrlEncoded
    Call<DummyResponse> signup(@Field("userId") String userId, @Field("password") String password, @Field("name") String name, @Field("studentNumber") String studentNumber, @Field("email") String email, @Field("lecture") ArrayList<String> lecture);
    //출력: success or fail

    @POST("user/login")
    @FormUrlEncoded
    Call<User> login(@Field("userId") String userId, @Field("password") String password);
    //출력 : id

    @GET("/user")
    Call<User> getUserInfo(@Query("userId") String userId);
    //출력 : name, userType, email, studentNum, lecture리스트

    //회원가입할 때 아이디 중복확인
    @POST("/user/confirm")
    @FormUrlEncoded
    Call<DummyResponse> getIdConfirm(@Field("userId") String userId);
    //출력 : success or fail

    //강의실 예약 필터링
    @GET("/reservation/list")
    Call<List<DummyLectureRoomReservationState>> getReservationList(@Query("date") String date, @Query("building") String[] building,
                                                                    @Query("startTime") int startTime, @Query("lastTime") int lastTime);
    //입력: 날짜(하나), 건물(리스트), 시작시간(하나), 종료시간(하나), //강의실 예약 인원 수(하나)
    //입력: {date: "YYYY-M-D", building: "성호관 율곡관 연암관" startTime: "0" lastTime: "3"}
    //출력: [{lectureroom: "성101", stateList: "R 0 0 0 1 L"}, {lectureroom: "성103", stateList: "R A A A L L"}]

    //강의실 정보 가져오기
    @GET("/")
    Call<DummyLectureroomInfo> getLectureroomInfo(@Query("lectureroom") String lectureroom);
    //입력: {lectureroom: 성101}
    //출력: {capacity: 50}

    //강의실 예약 저장하기
    @POST("/")
    Call<DummyReservationId> postReservation(@Query("date") String date, @Query("lectureroom") String lectureroom,
                                             @Query("startTime") int startTime, @Query("lastTime") int lastTime,
                                             @Query("userid") String userid, @Query("randomafter") boolean randomafter);
    //입력: 날짜(하나), 강의실(하나), 시작시간(하나), 종료시간(하나), 본인id(하나), 선지망 후추첨인 경우 랜덤 강의실 선택 여부
    //입력: {date: "YYYY-M-D" lectureRoom: "성101" startTime: "9:00" lastTime: "10:00", userid: "akdsnmk", randomafter: "true or false"}
    //동작: 예약 정보 저장
    //출력: {예약내역id: qninia} - 나중에 추가정보를 입력할 때 이 예약내역 id를 이용한다.

    //예약에 강의실 목적, 모임원 정보 저장하기
    @POST("/")
    Call<DummyResponse> postReservationDetail(@Query("reservationId") String reservationId, @Query("reservationIntent") String reservationIntent,
                                              @Query("userClassofsNum") int userClassofsNum, @Query("userClassofs") String[] userClassofs);
    //입력: {reservationId: "reservationId", reservationIntent: "studying algorithm", userClassofsNum: "3",
    // userClassofs: ["201520971", "201520000", "201520001"]}
    //동작: 학번을 가지고 userid와 매칭을 한다.
    //출력: {response: "success or fail"}

    //개인 예약정보 list를 받아온다.
    @GET("/")
    Call<List<DummyReservationList>> getReservationList(@Query("date") String date, @Query("tense") String tense, @Query("userid") String userid);
    //입력: 날짜, 과거, userid
    //입력: {date: "YYYY-M-D", tense: "future or past", userid: "userid"}
    //출력: [{reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101"}, ...]
    //출력: reservationId, 예약 날짜, 요일(day), 시작시간, 종료시간, 강의실 이름

    //서버에서 하나의 예약 정보 가져오기
    @GET("/")
    Call<DummyReservationDetail> getReservationDetail(@Query("reservationId") String reservationId);
    //입력: {reservationId: "reservationId"}
    //출력: {date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101",
    //userid: ["user1", "user2", ...], beforeUri: "beforeuri", afterUri: "afteruri, reservationIntent: "studying algorithm",
    // beforeUploadTime: "8:05", afterUploadTime: "10:20"}
    //만약 uri가 존재하지 않으면 ""(null)로 보냄 uploadtime도 마찬가지

    //예약내역에 사진 저장하기 (before)
    @POST("/")
    Call<DummyResponse> postBeforePicture(@Query("reservationId") String reservationId, @Query("beforeuri") String beforeuri, @Query("beforeuriuploadtime") String beforeuriuploadtime);
    //입력: {reservationId: "reservationId", beforeuri: "beforeuri", beforeuriuploadtime: "08:05"}
    //동작: 해당 reservationId에 beforeuri 사진 추가, 저장
    //출력: {response: "success or fail"}

    //예약내역에 사진 저장하기 (after)
    @POST("/")
    Call<DummyResponse> postAfterPicture(@Query("reservationId") String reservationId, @Query("afteruri") String afteruri, @Query("afteruriuploadtime") String afteruriuploadtime);
    //입력: {reservationId: "reservationId", afteruri: "afteruri", afteruriuploadtime: "10:20"}
    //동작: 해당 reservationId에 afteruri 사진 추가, 저장
    //출력: {response: "success or fail"}

    //test용 - 선지망 후추첨인 경우 서버에서 강의실 확정을 짓는다.
//    @POST("/")
//    Call<>

}
