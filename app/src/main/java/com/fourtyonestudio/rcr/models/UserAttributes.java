package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Riris.
 */

public class UserAttributes implements Serializable {

    private static final long serialVersionUID = -5314402916663977450L;
    @SerializedName("company-id")
    @Expose
    private Integer companyId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role-id")
    @Expose
    private Integer roleId;
    @SerializedName("role")
    @Expose
    private String role;

    public Integer getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
