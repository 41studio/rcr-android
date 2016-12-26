package com.fourtyonestudio.rcr.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class RegisterUserRequest {

    @SerializedName("user")
    @Expose
    private UserRequest user;
    @SerializedName("company")
    @Expose
    private String company;

    public RegisterUserRequest(String company, UserRequest user) {
        this.company = company;
        this.user = user;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

}
