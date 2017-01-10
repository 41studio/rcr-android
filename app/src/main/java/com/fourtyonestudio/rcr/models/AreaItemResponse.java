package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Riris.
 */

public class AreaItemResponse implements Serializable {

    private static final long serialVersionUID = -1142220622268210133L;
    @SerializedName("data")
    @Expose
    private AreaItemData data;

    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }


    public AreaItemData getData() {
        return data;
    }

    public void setData(AreaItemData data) {
        this.data = data;
    }

}
