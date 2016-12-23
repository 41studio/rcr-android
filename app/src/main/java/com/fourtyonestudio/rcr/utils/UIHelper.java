package com.fourtyonestudio.rcr.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fourtyonestudio.rcr.R;

public class UIHelper {
    public static void showSnackbar(View root, String msg) {
        Snackbar.make(root, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbar(View root, String msg, View.OnClickListener listener) {
        Snackbar.make(root, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, listener)
                .show();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
//
//    public static void showToastNoInet(Context context) {
//        Toast.makeText(context, Constant.MESSAGE.NO_INET, Toast.LENGTH_SHORT).show();
//    }

    public static ProgressDialog showProgressDialog(Activity activity) {
        ProgressDialog pDialog = ProgressDialog.show(activity, null, null, true, false);
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pDialog.setContentView(R.layout.progress_dialog_default);
        return pDialog;
    }

    public static void dismissDialog(ProgressDialog pDialog) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public static void showDialogBackground(RelativeLayout rl) {
        if (rl != null && rl.getVisibility() == View.GONE) {
            rl.setVisibility(View.VISIBLE);
        }
    }

    public static void showDialogBackground(RelativeLayout rl, View other) {
        if (other != null && other.getVisibility() == View.VISIBLE) {
            other.setVisibility(View.GONE);
        }
        if (rl != null) {
            rl.setVisibility(View.VISIBLE);
        }
    }

    public static void dismissDialogBackground(RelativeLayout rl) {
        if (rl != null && rl.getVisibility() == View.VISIBLE) {
            rl.setVisibility(View.GONE);
        }
    }

    public static void dismissDialogBackground(RelativeLayout rl, View other) {
        if (rl != null && rl.getVisibility() == View.VISIBLE) {
            rl.setVisibility(View.GONE);
        }
        if (other != null) {
            other.setVisibility(View.VISIBLE);
        }
    }

}
