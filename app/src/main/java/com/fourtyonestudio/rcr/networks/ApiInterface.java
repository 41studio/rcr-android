package com.fourtyonestudio.rcr.networks;

import com.fourtyonestudio.rcr.models.AreaDetailResponse;
import com.fourtyonestudio.rcr.models.AreaResponse;
import com.fourtyonestudio.rcr.models.LoginSession;

import retrofit2.Call;
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
    @FormUrlEncoded
    @POST("auth_user")
    Call<LoginSession> login(@Field("email") String email,
                             @Field("password") String password);

    @GET("areas/{id}")
    Call<AreaDetailResponse> getAreaDetail(@Header("Authorization") String auth,
                                           @Path("id") int id);

    @GET("areas")
    Call<AreaResponse> getArea(@Header("Authorization") String auth);
}
