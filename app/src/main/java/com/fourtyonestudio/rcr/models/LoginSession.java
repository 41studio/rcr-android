package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mohamadsodiq on 12/14/16.
 */

public class LoginSession implements Serializable {

    private static final long serialVersionUID = 4529959188054323728L;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("auth_token")
    @Expose
    private String auth_token;

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
