package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Riris.
 */

public class AppraisalsAttributes implements Serializable {

    private static final long serialVersionUID = 8948294145542608148L;
    @SerializedName("checked")
    @Expose
    private Boolean checked;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("result-detail")
    @Expose
    private AppraisalsResultDetail resultDetail;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public AppraisalsResultDetail getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(AppraisalsResultDetail resultDetail) {
        this.resultDetail = resultDetail;
    }

}
