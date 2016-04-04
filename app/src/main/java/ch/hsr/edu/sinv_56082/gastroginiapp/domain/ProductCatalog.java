package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

import java.io.Serializable;

public class ProductCatalog implements Serializable {
    private String name;

    public ProductCatalog(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
