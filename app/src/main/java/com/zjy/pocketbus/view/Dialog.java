package com.zjy.pocketbus.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * com.zjy.pocketbus.view
 * Created by 73958 on 2017/12/2.
 */

public class Dialog {

    private static ProgressDialog progressDialog = null;

    public static void showProgressDialog(Context context, String message) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void setMessage(String message){
        if(progressDialog != null){
            progressDialog.setMessage(message);
        }
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
