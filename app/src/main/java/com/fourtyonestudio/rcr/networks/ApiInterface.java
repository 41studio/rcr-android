package com.fourtyonestudio.rcr.networks;

import com.fourtyonestudio.rcr.models.AppraisalsResponse;
import com.fourtyonestudio.rcr.models.AreaData;
import com.fourtyonestudio.rcr.models.AreaDetailResponse;
import com.fourtyonestudio.rcr.models.AreaItemResponse;
import com.fourtyonestudio.rcr.models.AreaResponse;
import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.InviteUser;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.Roles;
import com.fourtyonestudio.rcr.models.UserListResponse;
import com.fourtyonestudio.rcr.models.UserResponse;
import com.fourtyonestudio.rcr.models.request.ItemAreaRequest;
import com.fourtyonestudio.rcr.models.request.RegisterUserRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Call<UserResponse> createUser(@Header("Authorization") String auth,
                                  @Field("user[email]") String email,
                                  @Field("user[name]") String name,
                                  @Field("user[password]") String password,
                                  @Field("user[password_confirmation]") String password_conf,
                                  @Field("user[role_id]") String role_id);

    @GET("users/{id}")
    Call<UserResponse> getUserDetail(@Header("Authorization") String auth,
                                     @Path("id") int id);

    @FormUrlEncoded
    @PUT("users/{id}")
    Call<UserResponse> updateUser(@Header("Authorization") String auth,
                                  @Path("id") int id,
                                  @Field("user[email]") String email,
                                  @Field("user[role_id]") String role,
                                  @Field("user[name]") String name,
                                  @Field("user[password]") String pass,
                                  @Field("user[password_confirmation]") String pass_conf);

    @FormUrlEncoded
    @PUT("users/accept_invitation")
    Call<LoginSession> acceptInvitation(@Field("invitation_token") String token,
                                        @Field("user[password]") String pass,
                                        @Field("user[password_confirmation]") String pass_conf);

    @GET("areas/{id}/items")
    Call<AreaResponse> getAreaItems(@Header("Authorization") String auth,
                                    @Path("id") int id);


    @FormUrlEncoded
    @POST("areas")
    Call<AreaData> postArea(@Header("Authorization") String auth,
                            @Field("area[name]") String name);

    @GET("areas")
    Call<AreaResponse> getArea(@Header("Authorization") String auth, @Query("page") int page);

    @GET("areas")
    Call<AreaResponse> searchArea(@Header("Authorization") String auth, @Query("name") String name);

    @GET("areas/{id}")
    Call<AreaItemResponse> getAreaItemsDate(@Header("Authorization") String auth,
                                            @Path("id") int id,
                                            @Query("date") String date,
                                            @Query("page") int page);


    @GET("areas/{id}")
    Call<AreaItemResponse> searchAreaItems(@Header("Authorization") String auth,
                                           @Path("id") int id,
                                           @Query("date") String date,
                                           @Query("name") String name);

    @FormUrlEncoded
    @POST("areas/{id}/clone")
    Call<AreaItemResponse> cloneArea(@Header("Authorization") String auth,
                                     @Path("id") int id,
                                     @Field("area[name]") String area_name);

    @FormUrlEncoded
    @PUT("areas/{id}")
    Call<AreaItemResponse> putArea(@Header("Authorization") String auth,
                                   @Path("id") int id,
                                   @Field("area[name]") String area_name);

    @FormUrlEncoded
    @POST("areas/{id}/items")
    Call<AreaData> postAreaItems(@Header("Authorization") String auth,
                                 @Path("id") int id,
                                 @Field("item[name]") String name,
                                 @Field("item[item_times_attributes][][time]") List<String> time_attributes);

    @FormUrlEncoded
    @POST("appraisals")
    Call<AppraisalsResponse> postAppraisals(@Header("Authorization") String auth,
                                            @Field("appraisal[item_time_id]") int time_id);

    @FormUrlEncoded
    @POST("users/invite ")
    Call<InviteUser> inviteUser(@Header("Authorization") String auth,
                                @Field("user[email]") String email,
                                @Field("user[name]") String name,
                                @Field("user[role_id]") String role_id);

    @FormUrlEncoded
    @PUT("appraisals/{id}")
    Call<AppraisalsResponse> putAppraisals(@Header("Authorization") String auth,
                                           @Path("id") int time_id,
                                           @Field("appraisal[indicator_id]") String indicator_id);

    @FormUrlEncoded
    @PUT("appraisals/{id}")
    Call<AppraisalsResponse> putAppraisalsDescription(@Header("Authorization") String auth,
                                                      @Path("id") int time_id,
                                                      @Field("appraisal[description]") String description,
                                                      @Field("appraisal[indicator_id]") String indicator_id);

    @FormUrlEncoded
    @POST("auth_user")
    Call<LoginSession> login(@Field("email") String email,
                             @Field("password") String password);

    @GET("indicators")
    Call<Indicators> getIndicators(@Header("Authorization") String auth);

    @GET("roles")
    Call<Roles> getRoles();

    @GET("users")
    Call<UserListResponse> getUsers(@Header("Authorization") String auth);

    @FormUrlEncoded
    @PUT("users/{id}")
    Call<UserResponse> putUsers(@Header("Authorization") String auth,
                                @Path("id") int id,
                                @Field("user[email]") String email,
                                @Field("user[name]") String name,
                                @Field("user[role_id]") String role_id);

    @DELETE("users/{id}")
    Call<String> deleteUsers(@Header("Authorization") String auth,
                             @Path("id") int id);

    @GET("areas/{id}")
    Call<AreaDetailResponse> getAreaDetail(@Header("Authorization") String auth,
                                           @Path("id") int id);

    @DELETE("areas/{id}")
    Call<String> deleteArea(@Header("Authorization") String auth,
                            @Path("id") int id);

    @DELETE("areas/{id}/items/{items_id}")
    Call<String> deleteItemArea(@Header("Authorization") String auth,
                                @Path("id") int id, @Path("items_id") int items_id);

    @PUT("areas/{id_areas}/items/{items_id}")
    Call<AreaDetailResponse> putItemsArea(@Header("Authorization") String auth,
                                          @Path("id_areas") int id, @Path("items_id") int items_id,
                                          @Body ItemAreaRequest itemAreaRequest);


//    @FormUrlEncoded
//    @POST("appraisals")
//    Call<AppraisalsResponse> postAppraisals(@Header("Authorization") String auth, @Field("appraisal[item_time_id]") String time_id, @Field("appraisal[indicator_id]") String indicator_id);


}
