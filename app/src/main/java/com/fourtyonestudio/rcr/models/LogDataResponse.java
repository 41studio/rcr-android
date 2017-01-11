package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Riris.
 */

public class LogDataResponse {
    @SerializedName("data")
    @Expose
    private List<LogDataDatum> data = null;

    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Meta getMeta() {
        return meta;
    }

    public List<LogDataDatum> getData() {
        return data;
    }

    public void setData(List<LogDataDatum> data) {
        this.data = data;
    }

}
