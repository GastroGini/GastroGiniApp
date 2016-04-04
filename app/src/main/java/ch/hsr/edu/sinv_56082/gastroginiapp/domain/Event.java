package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

public class Event extends Observable implements Serializable{
    private String title;
    private int amountOfTables = 0;
    private Date executionDate = new SimpleDateFormat().getCalendar().getTime();

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

    public Date getExecutionDate(){
        return executionDate;
    }

    public void setExecutionDate(String executionDate){
        try{
            this.executionDate = new SimpleDateFormat().parse(executionDate);
            setChanged();
            notifyObservers();
        }catch(ParseException ex){
            ex.printStackTrace();
        }
    }

}
