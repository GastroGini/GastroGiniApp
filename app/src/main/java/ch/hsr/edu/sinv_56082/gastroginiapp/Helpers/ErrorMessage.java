package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Phil on 14.05.2016.
 */
public class ErrorMessage {
    public ErrorMessage (Activity activity, String message){
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Error")
                .setMessage(message)
                .setNeutralButton("OK", null)
                .show();
    }
}
