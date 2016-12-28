package com.fourtyonestudio.rcr.networks;

import com.fourtyonestudio.rcr.models.AppraisalsResponse;
import com.fourtyonestudio.rcr.models.AreaData;
import com.fourtyonestudio.rcr.models.AreaDetailResponse;
import com.fourtyonestudio.rcr.models.AreaResponse;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.Roles;
import com.fourtyonestudio.rcr.models.request.RegisterUserRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public interface ApiInterface {

    @POST("register_user")
    Call<LoginSession> register(@Body RegisterUserRequest registerUserRequest);

    @FormUrlEncoded
    @POST("areas")
    Call<AreaData> postArea(@Header("Authorization") String auth, @Field("area[name]") String name);

    @GET("areas")
    Call<AreaResponse> getArea(@Header("Authorization") String auth);

    @GET("areas/{id}/items")
    Call<AreaResponse> getAreaItems(@Header("Authorization") String auth, @Path("id") int id);

    @FormUrlEncoded
    @POST("areas/{id}/items")
    Call<AreaData> postAreaItems(@Header("Authorization") String auth, @Path("id") int id, @Field("item[name]") String name, @Field("item[item_times_attributes][][time]") List<String> time_attributes);

    @FormUrlEncoded
    @POST("appraisals")
    Call<AppraisalsResponse> postAppraisals(@Header("Authorization") String auth, @Field("appraisal[item_time_id]") String time_id, @Field("appraisal[indicator_id]") String indicator_id);

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
