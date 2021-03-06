package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "ProductLists")
public class ProductList extends UUIDModel{

    public ProductList(){}

    public ProductList(String name){
        super();
        this.name = name;
    }

    @Column
    public String name;

    public List<Product> products(){
        return getMany(Product.class, "productList");
    }

    public List<Event> events(){
        return getMany(Event.class, "productList");
    }

    public String toString(){
        return name;
    }

}
