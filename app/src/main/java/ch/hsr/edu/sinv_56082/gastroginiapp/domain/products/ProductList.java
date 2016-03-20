package ch.hsr.edu.sinv_56082.gastroginiapp.domain.products;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "ProductLists")
public class ProductList extends UUIDModel{


    @Column
    public String name;

    public List<Product> products(){
        return getMany(Product.class, "productList");
    }

    public List<Event> events(){
        return getMany(Event.class, "productList");
    }

}