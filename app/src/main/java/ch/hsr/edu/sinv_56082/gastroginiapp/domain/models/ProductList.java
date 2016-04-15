package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

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
        return getMany(Product.class, "menucardRowItem");
    }

    public List<Event> events(){
        return getMany(Event.class, "menucardRowItem");
    }

    public String toString(){
        return name;
    }

    public static ProductList get(UUID uuid){
        return new Select().from(ProductList.class).where("uuid = ?", uuid.toString()).executeSingle();
    }

    public static ProductList get(String uuid){
        return get(UUID.fromString(uuid));
    }

}
