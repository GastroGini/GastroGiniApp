package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import java.io.Serializable;

public class EventTable implements Serializable {
    private String name;

    public EventTable(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
