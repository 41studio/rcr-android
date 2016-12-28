package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class UserData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private UserAttributes attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(UserAttributes attributes) {
        this.attributes = attributes;
    }

}
