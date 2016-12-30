package com.fourtyonestudio.rcr.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Riris.
 */

public class Item {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("item_times_attributes")
    @Expose
    private List<ItemTimeAttributesRequest> itemTimesAttributes = null;

    public Item(List<ItemTimeAttributesRequest> itemTimesAttributes, String name) {
        this.itemTimesAttributes = itemTimesAttributes;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemTimeAttributesRequest> getItemTimesAttributes() {
        return itemTimesAttributes;
    }

    public void setItemTimesAttributes(List<ItemTimeAttributesRequest> itemTimesAttributes) {
        this.itemTimesAttributes = itemTimesAttributes;
    }

}
