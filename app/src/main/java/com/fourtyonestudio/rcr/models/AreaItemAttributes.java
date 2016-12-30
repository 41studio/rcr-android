package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Riris.
 */

public class AreaItemAttributes implements Serializable {

    private static final long serialVersionUID = -3082498492016486019L;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("item-list")
    @Expose
    private List<AreaItemList> itemList = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<AreaItemList> itemList) {
        this.itemList = itemList;
    }

}