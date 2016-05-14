package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class WarningMessage {

    public WarningMessage (Activity activity, String message, DialogInterface.OnClickListener execute){
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Warnung")
                .setMessage(message)
                .setPositiveButton("Löschen", execute)
                .setNegativeButton("Abbrechen", null)
                .show();
    }




}
