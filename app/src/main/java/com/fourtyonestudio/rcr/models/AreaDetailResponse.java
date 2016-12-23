package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mohamadsodiq on 12/14/16.
 */

public class AreaDetailResponse implements Serializable {

    private static final long serialVersionUID = -7343853301200683684L;
    @SerializedName("data")
    @Expose
    private Area areas;

    public Area getAreas() {
        return areas;
    }
}
