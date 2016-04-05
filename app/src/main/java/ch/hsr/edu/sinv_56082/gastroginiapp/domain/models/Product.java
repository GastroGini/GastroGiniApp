package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;


import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

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