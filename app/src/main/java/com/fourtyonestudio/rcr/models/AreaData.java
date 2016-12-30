package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Riris.
 */

public class AreaData implements Serializable {

    private static final long serialVersionUID = -2447720625277261193L;
    @SerializedName("data")
    @Expose
    private Area data;

    public Area getData() {
        return data;
    }

    public void setData(Area data) {
        this.data = data;
    }

}
