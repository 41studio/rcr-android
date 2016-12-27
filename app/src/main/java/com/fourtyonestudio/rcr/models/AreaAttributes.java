package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mohamadsodiq on 12/14/16.
 */

public class AreaAttributes implements Serializable {

    private static final long serialVersionUID = -7935388372954307942L;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("times")
    @Expose
    private List<AreaTime> times = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaTime> getTimes() {
        return times;
    }

    public void setTimes(List<AreaTime> times) {
        this.times = times;
    }

}
