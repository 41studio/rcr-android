package com.fourtyonestudio.rcr.tables;

import com.orm.SugarRecord;

/**
 * Created by Riris.
 */

public class ItemAreaTable extends SugarRecord {

    private int id_area;
    private int id_times;
    private String time;
    private String indicator;

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public int getId_times() {
        return id_times;
    }

    public void setId_times(int id_times) {
        this.id_times = id_times;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
