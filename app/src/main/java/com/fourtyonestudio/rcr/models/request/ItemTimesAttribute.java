package com.fourtyonestudio.rcr.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class ItemTimesAttribute {
    @SerializedName("time")
    @Expose
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
