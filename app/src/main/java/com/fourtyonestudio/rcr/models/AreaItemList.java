package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Riris.
 */

public class AreaItemList implements Serializable {

    private static final long serialVersionUID = 5909028905027457303L;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("times")
    @Expose
    private List<AreaItemTime> times = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaItemTime> getTimes() {
        return times;
    }

    public void setTimes(List<AreaItemTime> times) {
        this.times = times;
    }

}
