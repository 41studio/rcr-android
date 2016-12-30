package com.fourtyonestudio.rcr.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class UserRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password_confirmation")
    @Expose
    private String passwordConfirmation;
//    @SerializedName("role_id")
//    @Expose
//    private String roleId;


    public UserRequest(String email, String name, String password, String passwordConfirmation) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
//        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

//    public String getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(String roleId) {
//        this.roleId = roleId;
//    }

}