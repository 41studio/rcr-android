package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class InviteUser {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("company_id")
    @Expose
    private int companyId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("role_id")
    @Expose
    private int roleId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("invitation_token")
    @Expose
    private String invitationToken;
    @SerializedName("invitation_created_at")
    @Expose
    private String invitationCreatedAt;
    @SerializedName("invitation_sent_at")
    @Expose
    private String invitationSentAt;
    @SerializedName("invitation_accepted_at")
    @Expose
    private String invitationAcceptedAt;
    @SerializedName("invitation_limit")
    @Expose
    private int invitationLimit;
    @SerializedName("invited_by_type")
    @Expose
    private String invitedByType;
    @SerializedName("invited_by_id")
    @Expose
    private int invitedById;
    @SerializedName("invitations_count")
    @Expose
    private int invitationsCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvitationToken() {
        return invitationToken;
    }

    public void setInvitationToken(String invitationToken) {
        this.invitationToken = invitationToken;
    }

    public String getInvitationCreatedAt() {
        return invitationCreatedAt;
    }

    public void setInvitationCreatedAt(String invitationCreatedAt) {
        this.invitationCreatedAt = invitationCreatedAt;
    }

    public String getInvitationSentAt() {
        return invitationSentAt;
    }

    public void setInvitationSentAt(String invitationSentAt) {
        this.invitationSentAt = invitationSentAt;
    }

    public String getInvitationAcceptedAt() {
        return invitationAcceptedAt;
    }

    public void setInvitationAcceptedAt(String invitationAcceptedAt) {
        this.invitationAcceptedAt = invitationAcceptedAt;
    }

    public int getInvitationLimit() {
        return invitationLimit;
    }

    public void setInvitationLimit(int invitationLimit) {
        this.invitationLimit = invitationLimit;
    }

    public String getInvitedByType() {
        return invitedByType;
    }

    public void setInvitedByType(String invitedByType) {
        this.invitedByType = invitedByType;
    }

    public int getInvitedById() {
        return invitedById;
    }

    public void setInvitedById(int invitedById) {
        this.invitedById = invitedById;
    }

    public int getInvitationsCount() {
        return invitationsCount;
    }

    public void setInvitationsCount(int invitationsCount) {
        this.invitationsCount = invitationsCount;
    }

}
