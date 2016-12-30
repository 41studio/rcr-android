package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Riris.
 */

public class AppraisalsResponse implements Serializable {

    private static final long serialVersionUID = -5867262592362136720L;
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