package com.fourtyonestudio.rcr.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.fourtyonestudio.rcr.models.Indicators;
import com.fourtyonestudio.rcr.models.LoginSession;
import com.fourtyonestudio.rcr.models.Roles;
import com.google.gson.Gson;

/**
 * Created by mohamadsodiq on 12/13/16.
 */

public class DataPreferences {

    private static final String KEY_LOG_SES = "rcr_log_ses";
    private static final String KEY_INDICATOR = "rcr_indicator";
    private static final String KEY_ROLE = "rcr_role";
    private static final String PREF_NAME = "rcr_pref";
    private static final String ROLE = "role";

    private static final String IS_LOAD_AREA = "rcr_load_area";
    private static final String IS_LOAD_AREA_ITEM = "rcr_load_area_item";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    public DataPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public LoginSession getLoginSession() {
        String s = pref.getString(KEY_LOG_SES, null);
        Gson gson = new Gson();
        return gson.fromJson(s, LoginSession.class);
    }

    public void setLoginSession(LoginSession loginSession) {
        Gson gson = new Gson();
        String s = gson.toJson(loginSession);
        editor.putString(KEY_LOG_SES, s);
        editor.apply();
    }

    public String getRole() {
        return pref.getString(ROLE, "");
    }

    public void setRole(String result) {
        editor.putString(ROLE, result);
        editor.commit();
    }

    public boolean isLoadArea() {
        return pref.getBoolean(IS_LOAD_AREA, false);
    }

    public void setLoadArea(boolean result) {
        editor.putBoolean(IS_LOAD_AREA, result);
        editor.commit();
    }

    public boolean isLoadAreaItem() {
        return pref.getBoolean(IS_LOAD_AREA_ITEM, false);
    }

    public void setLoadAreaItem(boolean result) {
        editor.putBoolean(IS_LOAD_AREA_ITEM, result);
        editor.commit();
    }

    public Indicators getIndicator() {
        String s = pref.getString(KEY_INDICATOR, null);
        Gson gson = new Gson();
        return gson.fromJson(s, Indicators.class);
    }

    public void setIndicators(Indicators indicators) {
        Gson gson = new Gson();
        String s = gson.toJson(indicators);
        editor.putString(KEY_INDICATOR, s);
        editor.apply();
    }

    public Roles getRoles() {
        String s = pref.getString(KEY_ROLE, null);
        Gson gson = new Gson();
        return gson.fromJson(s, Roles.class);
    }

    public void setRoles(Roles indicators) {
        Gson gson = new Gson();
        String s = gson.toJson(indicators);
        editor.putString(KEY_ROLE, s);
        editor.apply();
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }

    //
//        public List<Department> getDepartments() {
//            String s = pref.getString(KEY_INSTITUTION_DEPT, null);
//            if (s != null) {
//                Gson gson = new Gson();
//                Department[] arr = gson.fromJson(s, Department[].class);
//                return Arrays.asList(arr);
//            } else {
//                return null;
//            }
//        }
//
//        public void setDepartments(List<Department> list) {
//            Gson gson = new Gson();
//            String s = gson.toJson(list);
//            editor.putString(KEY_INSTITUTION_DEPT, s);
//            editor.commit();
//        }
}
