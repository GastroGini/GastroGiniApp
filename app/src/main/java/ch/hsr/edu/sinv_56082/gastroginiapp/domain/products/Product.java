package ch.hsr.edu.sinv_56082.gastroginiapp.domain.products;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.UUID;

@Table(name = "Products")
public class Product extends Model{

    @Column(unique = true)
    public UUID uuid;

    @Column
    public double price;

    @Column
    public String mass;

    @Column
    public Product productDescription;

    @Column
    public ProductList productList;


}