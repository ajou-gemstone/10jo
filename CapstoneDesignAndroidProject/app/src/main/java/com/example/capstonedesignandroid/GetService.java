package com.example.capstonedesignandroid;


import com.example.capstonedesignandroid.DTO.DummyCafeCoreInfo;
import com.example.capstonedesignandroid.DTO.DummyCurrentReservationBuildingFloor;
import com.example.capstonedesignandroid.DTO.DummyLectureRoomReservationState;
import com.example.capstonedesignandroid.DTO.DummyLectureroomInfo;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail;
import com.example.capstonedesignandroid.DTO.DummyReservationDetail2;
import com.example.capstonedesignandroid.DTO.DummyReservationDetailGuard;
import com.example.capstonedesignandroid.DTO.DummyReservationId;
import com.example.capstonedesignandroid.DTO.DummyStudentNameId;
import com.example.capstonedesignandroid.DTO.DummyTile;
import com.example.capstonedesignandroid.DTO.DummyTile2;
import com.example.capstonedesignandroid.DTO.DummySignUp;
import com.example.capstonedesignandroid.DTO.User;
import com.example.capstonedesignandroid.DTO.DummyReservationList;
import com.example.capstonedesignandroid.DTO.DummyResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetService {

    @POST("user/signup")
    Call<DummyResponse> signup(@Body DummySignUp dummySignUp);
    //출력: success or fail

    @POST("user/login")
    @FormUrlEncoded
    Call<User> login(@Field("userId") String userId, @Field("password") String password, @Field("token") String token);
    //출력 : id

    @GET("/user")
    Call<User> getUserInfo(@Query("userId") String userId);
    //출력 : name, userType, email, studentNum, lecture리스트

    //회원가입할 때 아이디 중복확인
    @POST("/user/confirm")
    @FormUrlEncoded
    Call<DummyResponse> getIdConfirm(@Field("userId") String userId);
    //출력 : success or fail

    //회원가입할 때 이메일 중복확인
    @POST("/user/email")
    @FormUrlEncoded
    Call<DummyResponse> postEmail(@Field("email") String email);
    //출력 : success or fail

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
    @POST("/reservation/updateInfo")
    Call<DummyResponse> postReservationDetail(@Body DummyReservationDetail2 dr2);
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
    //userId: ["user1", "user2", ...], beforeUri: "beforeuri", afterUri: "afteruri, reservationIntent: "studying algorithm",
    // beforeUploadTime: "8:05", afterUploadTime: "10:20", userName: [""]}
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
    Call<DummyResponse> deleteMyReservation(@Field("reservationId") String reservationId, @Field("score") int panelty);
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

    //경비원이 예약정보 list를 받아온다.o
    @GET("/reservation/guardBuildingInfo")
    Call<List<DummyReservationList>> getGuardReservationList(@Query("tense") String tense, @Query("buildingName") String buildingName);
    //입력: 시제, 건물명
    //입력: {tense: "future or past", buildingName: "성호관"}
    //출력: [{reservationId: "reservationId", date: "YYYY-MM-DD", day(요일): "월", startTime: "2", lastTime:"5", lectureRoom:"성101"}, ...]
    //출력: reservationId, 예약 날짜, 요일(day), 시작시간, 종료시간, 강의실 이름

    //학번이 유효한지 확인한다.o
    @FormUrlEncoded
    @POST("/reservation/searchStudentId")
    Call<DummyResponse> searchStudentId(@Field("studentId") String studentId);

    //카페 정보를 가져온다. 나중에 시간 추가로 가져오기.o
    @GET("/cafe/list")
    Call<List<DummyCafeCoreInfo>> getCafeInfoList();
    //입력: x
    //출력: [{cafeId: 3, name: 키브한커피 congestion: 5, latitude: 30.000000, longitude: 127.0000000, cafeBody: 설명, address: 주소}, ...]

    //현재 시간을 가져온다.
    @GET("/")
    Call<DummyResponse> getServerCurrentDate();
    //long nowTime = System.currentTimeMillis(); -->
    //var currentTimeMillis = new Date().getTime();
    //출력: response: 1589944246024

    //학생이 시간표 정보를 가져온다.o
    @GET("/timetable/info")
    Call<List<DummyTile>> getTimeTableInfo(@Query("userId") String userId);
    //입력: {userId: 4}
    //출력: [{contents: 캡스톤 디자인1팔333, time: A0}, {contents: 캡스톤 디자인1팔333, time: A1}, {contents: 캡스톤 디자인1팔333, time: A2},
    // {contents: 알고리즘1팔233, time: A10}, {contents: 알고리즘1팔233, time: A11}, {contents: 알고리즘1팔233, time: A12},
    // {contents: 동아리활동2아주대, time: B12}, {contents: 동아리활동2아주대, time: B13}]

    //학생이 시간표 정보를 업데이트 한다.o
    @POST("/timetable/update")
    Call<DummyResponse> postTimeTableInfo(@Body DummyTile2 dummyTile2);
    //(강의 정보는 서버에서 관리, 받아오기만 한다. 업데이트는 개인 정보만이다.)
    //입력: {userId: 4, info: [{contents: 동아리활동2아주대, time: B12}, {contents: 동아리활동2아주대, time: B13}]}

    //스터디원의 비교된 시간표를 가져온다.
    @GET("/timetable/time")
    Call<List<DummyTile>> getTimeTableCompared(@Query("groupId") String groupId);
    //contents에는 겹치는 명 수가 들어가면 된다. (0명이면 안와도 된다.)
    //출력: [{contents: 2, time: A0}, {contents: 2, time: A1}, {contents: 2, time: A2},
    // {contents: 1, time: A10}, {contents: 1, time: A11}, {contents: 1, time: A12},
    // {contents: 4, time: B12}, {contents: 4, time: B13}]

    //test용 - 선지망 후추첨인 경우 서버에서 강의실 확정을 짓는다.
    @FormUrlEncoded
    @POST("/")
    Call<DummyResponse> postDrawDate(@Field("date") String date);

}
