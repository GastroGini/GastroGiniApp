package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;

public class DateHelpers {
    public interface Callback {
        public void onSet(Date date);
    }

    public static class Picker {
        public int year, month, day;

        public Picker(Context context, final Callback callback) {


            final View dialogViewTime = View.inflate(context, R.layout.timepicker_util, null);
            final AlertDialog alertDialogTime = new AlertDialog.Builder(context).create();

            dialogViewTime.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TimePicker timePicker = (TimePicker) dialogViewTime.findViewById(R.id.time_picker);
                    Calendar calendar = new GregorianCalendar(year,
                            month,
                            day,
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute());

                    callback.onSet(new Date(calendar.getTimeInMillis()));
                    alertDialogTime.dismiss();
                }
            });
            alertDialogTime.setView(dialogViewTime);
            alertDialogTime.show();


            final View dialogView = View.inflate(context, R.layout.datepicker_util, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

            dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                    year = datePicker.getYear();
                    month = datePicker.getMonth();
                    day = datePicker.getDayOfMonth();
                    alertDialog.dismiss();
                }
            });
            alertDialog.setView(dialogView);
            alertDialog.show();
        }
    }

    public static String dateToString(Context context, Date date){
        return new SimpleDateFormat(context.getString(R.string.date_format)).format(date);
    }

}