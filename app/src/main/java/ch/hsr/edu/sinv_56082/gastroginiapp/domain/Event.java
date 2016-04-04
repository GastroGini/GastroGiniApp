package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;

public class Event extends Observable implements Serializable{
    private String title;
    private long DAY_IN_MILLISECONDS = 86400000;
    private int amountOfTables = 0;
    private Date startTime = new Date();
    private Date endTime = new Date(startTime.getTime() + DAY_IN_MILLISECONDS);
    private List<EventTable> tables = new ArrayList<>();
    private ProductList productList;

    public Event(String title){
        this.title = title;
        this.productList = new ProductList("Dummy set");
    }

    public Event(String title,int amountOfTables){
        this.title = title;
        this.amountOfTables = amountOfTables;
        constructTables();
        this.productList = new ProductList("Dummy set");
    }

    private void constructTables() {
        if(this.amountOfTables > tables.size()){
            for(int i = tables.size();i < this.amountOfTables;i++){
                tables.add(new EventTable("Table " + i));
            }
        }

        if(this.amountOfTables < tables.size()){
            for(int i = tables.size(); i > this.amountOfTables;i--){
                tables.remove(i-1);
            }
        }
    }

    public Event(String title, int amountOfTables,ProductList productList){
        this.title = title;
        this.amountOfTables = amountOfTables;
        this.productList = productList;
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
        constructTables();
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

    public List<EventTable> getTables(){
        return tables;
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
