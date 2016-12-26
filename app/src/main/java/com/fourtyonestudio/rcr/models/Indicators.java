package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Riris.
 */

public class Indicators implements Serializable {


    private static final long serialVersionUID = -309049807666250984L;
    @SerializedName("data")
    @Expose
    private List<IndicatorsDatum> data = null;

    public List<IndicatorsDatum> getData() {
        return data;
    }

    public void setData(List<IndicatorsDatum> data) {
        this.data = data;
    }

}