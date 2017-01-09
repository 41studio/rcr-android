package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class InviteResponse {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("user")
    @Expose
    private InviteUser user;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public InviteUser getUser() {
        return user;
    }

    public void setUser(InviteUser user) {
        this.user = user;
    }

}
