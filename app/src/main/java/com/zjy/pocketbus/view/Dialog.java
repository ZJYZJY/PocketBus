package com.zjy.pocketbus.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * com.zjy.pocketbus.view
 * Created by 73958 on 2017/12/2.
 */

public class Dialog {

    private static ProgressDialog progressDialog = null;

    public static void showProgressDialog(Context context, String info) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(info);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
