package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class AreaItemAppraisal {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("item-time-id")
    @Expose
    private Integer itemTimeId;
    @SerializedName("indicator-id")
    @Expose
    private Object indicatorId;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("checked")
    @Expose
    private Boolean checked;
    @SerializedName("manager-id")
    @Expose
    private Object managerId;
    @SerializedName("helper-id")
    @Expose
    private Integer helperId;
    @SerializedName("indicator-code")
    @Expose
    private String indicator;
    @SerializedName("checked-at")
    @Expose
    private String checkedAt;
    @SerializedName("reviewed-at")
    @Expose
    private String reviewedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemTimeId() {
        return itemTimeId;
    }

    public void setItemTimeId(Integer itemTimeId) {
        this.itemTimeId = itemTimeId;
    }

    public Object getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Object indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Object getManagerId() {
        return managerId;
    }

    public void setManagerId(Object managerId) {
        this.managerId = managerId;
    }

    public Integer getHelperId() {
        return helperId;
    }

    public void setHelperId(Integer helperId) {
        this.helperId = helperId;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(String checkedAt) {
        this.checkedAt = checkedAt;
    }

    public String getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(String reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

}