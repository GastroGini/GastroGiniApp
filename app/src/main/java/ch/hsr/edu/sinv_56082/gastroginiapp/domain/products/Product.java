package ch.hsr.edu.sinv_56082.gastroginiapp.domain.products;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Orders.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.UUIDModel;

@Table(name = "Products")
public class Product extends UUIDModel{

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