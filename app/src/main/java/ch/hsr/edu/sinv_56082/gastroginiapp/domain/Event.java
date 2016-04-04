package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;

public class Event extends Observable implements Serializable{
    private String title;
    private long DAY_IN_MILLISECONDS = 86400000;
    private int amountOfTables = 0;
    private Date startTime = new Date();
    private Date endTime = new Date(startTime.getTime() + DAY_IN_MILLISECONDS);
    private ProductList productList;

    public Event(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
        setChanged();
        notifyObservers();
    }

    public int getAmountOfTables(){
        return amountOfTables;
    }

    public void setAmountOfTables(int amountOfTables){
        this.amountOfTables = amountOfTables;
        setChanged();
        notifyObservers();
    }

    public String getStartTime(){
        return convertDateToString(startTime);
    }

    public void setStartTime(String startTime){
        convertStringToDate(startTime,this.startTime);
    }

    public String getEndTime(){
        return convertDateToString(endTime);
    }

    public void setEndTime(String endTime){
        convertStringToDate(endTime, this.endTime);
    }

    private void convertStringToDate(String time, Date date) {
        try{
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
            date = df.parse(time);
            setChanged();
            notifyObservers();
        }catch(ParseException ex){
            ex.printStackTrace();
        }
    }

    private String convertDateToString(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        String dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)+ "";
        String month = cal.get(Calendar.MONTH) + 1 + "";
        String year = cal.get(Calendar.YEAR) + "";
        if(dayOfMonth.length() == 1){
            dayOfMonth = "0" + dayOfMonth;
        }
        if(month.length() == 1){
            month = "0" + month;
        }
        String date = dayOfMonth + "." + month + "." + year;
        return date;
    }

}
