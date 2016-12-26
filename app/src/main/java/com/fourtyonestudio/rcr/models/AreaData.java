package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class AreaData {
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
