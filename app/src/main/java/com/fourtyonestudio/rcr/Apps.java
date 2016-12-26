package com.fourtyonestudio.rcr;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Riris.
 */

public class Apps extends Application {
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
