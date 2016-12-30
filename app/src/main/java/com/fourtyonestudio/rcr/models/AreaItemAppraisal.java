package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Riris.
 */

public class AreaItemAppraisal implements Serializable {

    private static final long serialVersionUID = 665020009311941998L;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("item-time-id")
    @Expose
    private Integer itemTimeId;
    @SerializedName("indicator-id")
    @Expose
    private Integer indicatorId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("checked")
    @Expose
    private Boolean checked;
    @SerializedName("manager-id")
    @Expose
    private Integer managerId;
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

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
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