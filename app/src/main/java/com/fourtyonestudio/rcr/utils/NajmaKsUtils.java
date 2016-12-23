package com.fourtyonestudio.rcr.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by mohamadsodiq on 9/5/16.
 */
public class NajmaKsUtils {
    public static void openMaps(Context context, String address) {
        if (context != null) {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            }
        }
    }
}
