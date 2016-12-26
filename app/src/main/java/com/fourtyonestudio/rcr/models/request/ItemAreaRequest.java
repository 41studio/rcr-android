package com.fourtyonestudio.rcr.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Riris.
 */

public class ItemAreaRequest {
    @SerializedName("item")
    @Expose
    private Item item;
    @SerializedName("area_id")
    @Expose
    private String areaId;

    public ItemAreaRequest(String areaId, Item item) {
        this.areaId = areaId;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

}
