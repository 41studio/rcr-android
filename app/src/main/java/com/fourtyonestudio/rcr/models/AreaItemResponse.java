package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class AreaItemResponse {
    @SerializedName("data")
    @Expose
    private AreaItemData data;

    public AreaItemData getData() {
        return data;
    }

    public void setData(AreaItemData data) {
        this.data = data;
    }

}
