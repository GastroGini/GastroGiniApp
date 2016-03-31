package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import java.io.Serializable;
import java.util.Observable;

public class Fest extends Observable implements Serializable {
    private String title;

    public Fest(String title){
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
}
