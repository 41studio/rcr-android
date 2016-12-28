package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mohamadsodiq on 12/14/16.
 */

public class LoginSession implements Serializable {

    private static final long serialVersionUID = 4529959188054323728L;
    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("user")
    @Expose
    private User user;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
