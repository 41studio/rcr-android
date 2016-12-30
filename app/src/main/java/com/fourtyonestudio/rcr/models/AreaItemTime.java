package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Riris.
 */

public class AreaItemTime implements Serializable {

    private static final long serialVersionUID = 3145204544872851134L;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("appraisals")
    @Expose
    private AreaItemAppraisal appraisals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AreaItemAppraisal getAppraisals() {
        return appraisals;
    }

    public void setAppraisals(AreaItemAppraisal appraisals) {
        this.appraisals = appraisals;
    }

}