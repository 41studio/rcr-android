package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class Area implements Serializable {

    private static final long serialVersionUID = -8936886655655701647L;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private AreaAttributes attributes;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public AreaAttributes getAttributes() {
        return attributes;
    }


}
