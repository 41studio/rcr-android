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
    private List<ItemTimesAttribute> itemTimesAttributes = null;

    public Item(List<ItemTimesAttribute> itemTimesAttributes, String name) {
        this.itemTimesAttributes = itemTimesAttributes;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemTimesAttribute> getItemTimesAttributes() {
        return itemTimesAttributes;
    }

    public void setItemTimesAttributes(List<ItemTimesAttribute> itemTimesAttributes) {
        this.itemTimesAttributes = itemTimesAttributes;
    }

}