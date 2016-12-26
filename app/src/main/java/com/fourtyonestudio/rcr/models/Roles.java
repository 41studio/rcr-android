package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Riris.
 */

public class Roles implements Serializable {

    private static final long serialVersionUID = -7440428762333859881L;
    @SerializedName("data")
    @Expose
    private List<RolesDatum> data = null;

    public List<RolesDatum> getData() {
        return data;
    }

    public void setData(List<RolesDatum> data) {
        this.data = data;
    }

}
