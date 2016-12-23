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

    public List<Area> getAreas() {
        return areas;
    }
}
