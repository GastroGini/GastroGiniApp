package ch.hsr.edu.sinv_56082.gastroginiapp.domain;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ListProducts")
public class ProductListProduct extends Model{

    @Column
    public Product product;

    @Column
    public ProductList productList;


}