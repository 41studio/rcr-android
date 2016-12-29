package com.fourtyonestudio.rcr.networks;

import com.fourtyonestudio.rcr.models.AppraisalsResponse;
import com.fourtyonestudio.rcr.models.AreaData;
import com.fourtyonestudio.rcr.models.AreaDetailResponse;
import com.fourtyonestudio.rcr.models.AreaItemResponse;
import com.fourtyonestudio.rcr.models.AreaResponse;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.Roles;
import com.fourtyonestudio.rcr.models.UserResponse;
import com.fourtyonestudio.rcr.models.request.RegisterUserRequest;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public interface ApiInterface {

    @POST("register_user")
    Call<LoginSession> register(@Body RegisterUserRequest registerUserRequest);

    @FormUrlEncoded
    @POST("users")
    Call<UserResponse> createUser(@Header("Authorization") String auth, @Field("user[email]") String email, @Field("user[password]") String password, @Field("user[password_confirmation]") String password_conf, @Field("user[role_id]") String role_id);

    @FormUrlEncoded
    @POST("areas")
    Call<AreaData> postArea(@Header("Authorization") String auth, @Field("area[name]") String name);

    @GET("areas")
    Call<AreaResponse> getArea(@Header("Authorization") String auth);

    @GET("areas/{id}/items")
    Call<AreaResponse> getAreaItems(@Header("Authorization") String auth, @Path("id") int id);

    @GET("areas/{id}")
    Call<AreaItemResponse> getAreaItemsDate(@Header("Authorization") String auth, @Path("id") int id, @Query("date") String date);

    @FormUrlEncoded
    @PUT("areas/{id}")
    Call<AreaItemResponse> putArea(@Header("Authorization") String auth, @Path("id") int id, @Field("area[name]") String area_name);

    @FormUrlEncoded
    @POST("areas/{id}/items")
    Call<AreaData> postAreaItems(@Header("Authorization") String auth, @Path("id") int id, @Field("item[name]") String name, @Field("item[item_times_attributes][][time]") List<String> time_attributes);

    @FormUrlEncoded
    @POST("appraisals")
    Call<AppraisalsResponse> postAppraisals(@Header("Authorization") String auth, @Field("appraisal[item_time_id]") int time_id);

    @POST("areas/{id_areas}/items/{id_item}")
    Call<AppraisalsResponse> putItemAreas(@Header("Authorization") String auth, @Path("id_areas") int id_areas, @Path("id_item") int id_item, @Body JSONObject jsonObject);


    @FormUrlEncoded
    @PUT("appraisals/{id}")
    Call<AppraisalsResponse> putAppraisals(@Header("Authorization") String auth, @Path("id") int time_id, @Field("appraisal[indicator_id]") String indicator_id);

//    @FormUrlEncoded
//    @POST("appraisals")
//    Call<AppraisalsResponse> postAppraisals(@Header("Authorization") String auth, @Field("appraisal[item_time_id]") String time_id, @Field("appraisal[indicator_id]") String indicator_id);

    @FormUrlEncoded
    @POST("auth_user")
    Call<LoginSession> login(@Field("email") String email,
                             @Field("password") String password);

    @GET("indicators")
    Call<Indicators> getIndicators(@Header("Authorization") String auth);

    @GET("roles")
    Call<Roles> getRoles();

    @GET("areas/{id}")
    Call<AreaDetailResponse> getAreaDetail(@Header("Authorization") String auth,
                                           @Path("id") int id);


}
