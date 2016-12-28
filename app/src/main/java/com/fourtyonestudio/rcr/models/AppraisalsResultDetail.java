package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class AppraisalsResultDetail {
    @SerializedName("indicator")
    @Expose
    private String indicator;
    @SerializedName("helper")
    @Expose
    private String helper;
    @SerializedName("manager")
    @Expose
    private Object manager;
    @SerializedName("checked-at")
    @Expose
    private String checkedAt;
    @SerializedName("approved-at")
    @Expose
    private String approvedAt;

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getHelper() {
        return helper;
    }

    public void setHelper(String helper) {
        this.helper = helper;
    }

    public Object getManager() {
        return manager;
    }

    public void setManager(Object manager) {
        this.manager = manager;
    }

    public String getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(String checkedAt) {
        this.checkedAt = checkedAt;
    }

    public String getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(String approvedAt) {
        this.approvedAt = approvedAt;
    }

}



