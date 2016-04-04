package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

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
        this.productList = new ProductList("Dummy set");
    }

    public ProductList getProductList(){
        return productList;
    }

    public void setProductList(ProductList productList){
        this.productList = productList;
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

    public void setAmountOfTables(String amountOfTables){
        if(amountOfTables.isEmpty()){
            this.amountOfTables = 0;
        }else{
            this.amountOfTables = Integer.parseInt(amountOfTables);
        }
        setChanged();
        notifyObservers();
    }

    public String getStartTime(){
        return convertDateToString(startTime);
    }

    public void setStartTime(String startTime){
        this.startTime = convertStringToDate(startTime);
        setChanged();
        notifyObservers();
    }

    public String getEndTime(){
        return convertDateToString(endTime);
    }

    public void setEndTime(String endTime){
        this.endTime = convertStringToDate(endTime);
        setChanged();
        notifyObservers();
    }

    private Date convertStringToDate(String time) {
        Date date = new Date();
        try{
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
            date = df.parse(time);
        }catch(ParseException ex){
            ex.printStackTrace();
        }
        return date;
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
