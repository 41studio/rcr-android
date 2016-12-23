package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class ItemTimes implements Serializable {

    private static final long serialVersionUID = 2030865505914814792L;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("time")
    @Expose
    private String time;
    private Indicator indicator;

    public ItemTimes(int item_id, String time) {
        this.id = item_id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public static List<ItemTimes> getDummyItemTimes() {
        List<ItemTimes> list = new ArrayList<>();
        list.add(new ItemTimes(1, "9:00"));
        list.add(new ItemTimes(2, "14:00"));
        return list;
    }
}
