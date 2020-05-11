package com.example.capstonedesignandroid;


import com.example.capstonedesignandroid.DTO.DummyCurrentReservationBuildingFloor;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyLectureroomInfo;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationDetailGuard;
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
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetService {

    @FormUrlEncoded
    @POST("/login")
    Call<List<User>> postUser(@Field("id") String id, @Field("password") String password);

    //강의실 예약 필터링o
    @GET("/reservation/list")
    Call<List<DummyLectureRoomReservationState>> getReservationList(@Query("date") String date, @Query("building") List<String> building,
                                                                    @Query("startTime") int startTime, @Query("lastTime") int lastTime);
    //입력: 날짜(하나), 건물(리스트), 시작시간(하나), 종료시간(하나), //강의실 예약 인원 수(하나)
    //입력: {date: "YYYY-M-D", building: "성호관 율곡관 연암관" startTime: "0" lastTime: "3"}
    //출력: [{lectureroom: "성101", stateList: "R 0 0 0 1 L"}, {lectureroom: "성103", stateList: "R A A A L L"}]

    //강의실 정보 가져오기o
    @GET("/lecture/info")
    Call<DummyLectureroomInfo> getLectureroomInfo(@Query("lectureRoom") String lectureroom);
    //입력: {lectureRoom: 성101}
    //출력: {{lectureRoomNum: 50}

    //강의실 예약 저장하기o
    @FormUrlEncoded
    @POST("/reservation/create")
    Call<DummyReservationId> postReservation(@Field("date") String date, @Field("lectureRoom") String lectureroom,
                                             @Field("startTime") int startTime, @Field("lastTime") int lastTime,
                                             @Field("userId") String userid, @Field("randomAfter") boolean randomafter);
    //입력: 날짜(하나), 강의실(하나), 시작시간(하나), 종료시간(하나), 본인id(하나), 선지망 후추첨인 경우 랜덤 강의실 선택 여부
    //입력: {date: "YYYY-M-D" lectureRoom: "성101" startTime: "9:00" lastTime: "10:00", userid: "akdsnmk", randomafter: "true or false"}
    //동작: 예약 정보 저장
    //출력: {reservationId: qninia} - 나중에 추가정보를 입력할 때 이 예약내역 id를 이용한다.

    //예약에 강의실 목적, 모임원 정보 저장하기o
    @FormUrlEncoded
    @POST("/reservation/updateInfo")
    Call<DummyResponse> postReservationDetail(@Field("reservationId") String reservationId, @Field("reservationIntent") String reservationIntent,
                                              @Field("userClassofsNum") int userClassofsNum, @Field("userClassofs") String[] userClassofs);
    //입력: {reservationId: "reservationId", reservationIntent: "studying algorithm", userClassofsNum: "3",
    // userClassofs: ["201520971", "201520000", "201520001"]}
    //동작: 학번을 가지고 userid와 매칭을 한다.
    //출력: {response: "success or fail"} 추가로 학번에 해당하는 사람이 없으면 fail return

    //개인 예약정보 list를 받아온다.o
    @GET("/reservation/myInfo")
    Call<List<DummyReservationList>> getReservationList(@Query("tense") String tense, @Query("userId") String userid);
    //입력: 날짜, 과거, userid
    //입력: {date: "YYYY-M-D", tense: "future or past", userId: "userId"}
    //출력: [{reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "2", lastTime:"5", lectureRoom:"성101"}, ...]
    //출력: reservationId, 예약 날짜, 요일(day), 시작시간, 종료시간, 강의실 이름

    //서버에서 하나의 예약 정보 가져오기o
    @GET("/reservation/info")
    Call<DummyReservationDetail> getReservationDetail(@Query("reservationId") String reservationId);
    //입력: {reservationId: "reservationId"}
    //출력: {date: "YYYY-MM-DD", day(요일): "월", startTime: "8:00", lastTime:"10:00", lectureRoom:"성101",
    //userid: ["user1", "user2", ...], beforeUri: "beforeuri", afterUri: "afteruri, reservationIntent: "studying algorithm",
    // beforeUploadTime: "8:05", afterUploadTime: "10:20"}
    //만약 uri가 존재하지 않으면 ""(null)로 보냄 uploadtime도 마찬가지

    //예약내역에 사진 저장하기 (before)o
    @FormUrlEncoded
    @POST("/reservation/beforeImage")
    Call<DummyResponse> postBeforePicture(@Field("reservationId") String reservationId, @Field("beforeUri") String beforeuri, @Field("beforeUriUploadTime") String beforeuriuploadtime);
    //입력: {reservationId: "reservationId", beforeUri: "beforeuri", beforeUriUploadTime: "08:05"}
    //동작: 해당 reservationId에 beforeuri 사진 추가, 저장
    //출력: {response: "success or fail"}

    //예약내역에 사진 저장하기 (after)o
    @FormUrlEncoded
    @POST("/reservation/afterImage")
    Call<DummyResponse> postAfterPicture(@Field("reservationId") String reservationId, @Field("afterUri") String afteruri, @Field("afterUriUploadTime") String afteruriuploadtime);
    //입력: {reservationId: "reservationId", afteruri: "afterUri", afterUriUploadTime: "10:20"}
    //동작: 해당 reservationId에 afteruri 사진 추가, 저장
    //출력: {response: "success or fail"}

    //내 예약 삭제하기o
    @FormUrlEncoded
    @POST("/reservation/delete")
    Call<DummyResponse> deleteMyReservation(@Field("reservationId") String reservationId);
    //입력: {reservationId: "reservationId"}
    //출력: {response: "success or fail"}

    //강의실 실시간 사용 상태 확인하기o
    @GET("/reservation/buildingInfo")
    Call<List<DummyCurrentReservationBuildingFloor>> getCurrentReservationBuildingFloor(@Query("buildingName") String buildingName, @Query("floor") String floor);
    //입력: 건물(하나), 층(하나)
    //입력: {buildingName: "성호관", floor: "1"}
    //출력: [배열]
    //출력: [{lectureRoomId: "강의실id", lectureRoom: "성101", reservationId: "예약id", startTime: "2", lastTime: "6",
    // reservationType: "R", userId: ["1, user2, user3"]}]

    //경비원 추가 예약 정보 가져오기o
    @GET("/reservation/guardInfo")
    Call<DummyReservationDetailGuard> getReservationDetailGuard(@Query("reservationId") String reservationId);
    //입력: {reservationId: "1"}
    //출력: {leaderId: "1 (예약자 id)", score: "5 (1 ~ 5)", scoreReason: "더럽게 사용함", guardId: "100 (경비원 id)"}
    //score와 scoreReason이 존재하지 않는 경우는 null 출력, guardId도 이미 평가한 경우만 출력 아니면 null

    //경비원 평가 저장하기o
    @FormUrlEncoded
    @POST("/reservation/saveScore")
    Call<DummyResponse> postSaveReservationDetailGuard(@Field("reservationId") String reservationId, @Field("score") Float score
    , @Field("scoreReason") String scoreReason, @Field("leaderId") String leaderId, @Field("guardId") String guardId);
    //입력: {reservationId: "1 (예약 id)", leaderId: "1 (예약자 id)", score: "5 (1 ~ 5)", scoreReason: "더럽게 사용함", guardId: "100 (경비원 id)"}
    //동작: 예약 목록에 위 내용을 업데이트 하고, leaderId의 score에 score만큼 감점을 시킨다.
    //출력: {response: success}


    //test용 - 선지망 후추첨인 경우 서버에서 강의실 확정을 짓는다.
//    @POST("/")
//    Call<>

}
