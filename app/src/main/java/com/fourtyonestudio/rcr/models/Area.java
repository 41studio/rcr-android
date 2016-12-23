package com.fourtyonestudio.rcr.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class Area implements Serializable {

    private static final long serialVersionUID = -8936886655655701647L;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private AreaAttributes attributes;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public AreaAttributes getAttributes() {
        return attributes;
    }

    //    private String name;
//    private int company_id;
//
//    public Area(int id, String name, int company_id) {
//        this.id = id;
//        this.name = name;
//        this.company_id = company_id;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTime() {
//        return name;
//    }
//
//    public void setTime(String name) {
//        this.name = name;
//    }
//
//    public int getCompany_id() {
//        return company_id;
//    }
//
//    public void setCompany_id(int company_id) {
//        this.company_id = company_id;
//    }
//
//    public static List<Area> getDummyAreas() {
//        List<Area> list = new ArrayList<>();
//        list.add(new Area(1, "Gedung A Laintai 1", 1));
//        list.add(new Area(2, "Gedung A Laintai 2", 1));
//        list.add(new Area(3, "Gedung B Laintai 1", 1));
//        list.add(new Area(4, "Gedung B Laintai 2", 1));
//        return list;
//    }
}
