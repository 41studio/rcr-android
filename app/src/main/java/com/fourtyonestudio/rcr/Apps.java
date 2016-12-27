package com.fourtyonestudio.rcr;

import android.app.Application;

import com.orm.SugarApp;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Riris.
 */

public class Apps extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/proximanova_light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }
}
