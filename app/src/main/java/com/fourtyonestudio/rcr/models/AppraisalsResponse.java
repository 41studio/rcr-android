package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class AppraisalsResponse {
    @SerializedName("data")
    @Expose
    private AppraisalsData data;

    public AppraisalsData getData() {
        return data;
    }

    public void setData(AppraisalsData data) {
        this.data = data;
    }

}