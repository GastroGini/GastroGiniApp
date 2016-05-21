package ch.hsr.edu.sinv_56082.gastroginiapp.Helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;

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
