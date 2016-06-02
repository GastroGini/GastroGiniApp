package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

/**
 * Created by Phil on 14.05.2016.
 */
public class HintMessage {
    public HintMessage(Activity activity, String title, String message){
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton("OK", null)
                .show();
    }
}
