package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class UserResponse {
    @SerializedName("data")
    @Expose
    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

}
