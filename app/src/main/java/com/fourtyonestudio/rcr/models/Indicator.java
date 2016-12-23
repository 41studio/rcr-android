package com.fourtyonestudio.rcr.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class Indicator implements Serializable {

    private static final long serialVersionUID = 4203321584487060949L;
    private String code;
    private String description;

    public Indicator(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<Indicator> getDummyIndicators() {
        List<Indicator> list = new ArrayList<>();
        list.add(new Indicator("B", "Bersih"));
        list.add(new Indicator("C", "Cukup"));
        list.add(new Indicator("K", "Kotor"));
        return list;
    }
}
