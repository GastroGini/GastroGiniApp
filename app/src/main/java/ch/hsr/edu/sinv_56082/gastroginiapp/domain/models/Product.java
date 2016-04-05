package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Products")
public class Product extends UUIDModel{

    public Product(){}

    public Product(ProductDescription productDescription,
                   ProductList productList,
                   double price,
                   String volume){
        super();
        this.productDescription = productDescription;
        this.productList = productList;
        this.price = price;
        this.volume = volume;
    }

    public static Product get(UUID uuid){
        return new Select().from(Product.class).where("uuid = ?", uuid).executeSingle();
    }

    public static Product get(String uuid){
        return get(UUID.fromString(uuid));
    }

    @Column
    public double price;

    @Column
    public String volume;

    @Column
    public ProductDescription productDescription;

    @Column
    public ProductList productList;


    public List<OrderPosition> orderPositions(){
        return getMany(OrderPosition.class, "product");
    }


}