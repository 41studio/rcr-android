package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mohamadsodiq on 12/14/16.
 */

public class AreaResponse implements Serializable {

    private static final long serialVersionUID = -1782939532449533048L;
    @SerializedName("data")
    @Expose
    private List<Area> areas;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Area> getAreas() {
        return areas;
    }

    public void setData(List<Area> data) {
        this.areas = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
