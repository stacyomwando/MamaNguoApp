package com.example.mamanguo.helpers;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.mamanguo.R;

public class UIFeatures {

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        new android.os.Handler().postDelayed(
                () -> progressDialog.dismiss(), 3000);
    }
}
